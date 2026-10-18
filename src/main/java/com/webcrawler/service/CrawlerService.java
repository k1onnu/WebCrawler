package com.webcrawler.service;

import com.webcrawler.concurrency.CrawlerWorker;
import com.webcrawler.concurrency.FileLogger;
import com.webcrawler.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerService {

    private final ExecutorService crawlerPool;
    private final ContactRepository repository;
    private final HtmlParser htmlParser;
    private final FileLogger fileLogger;

    /**
     * Запускает краулер и ЖДЁТ завершения работы всех воркеров.
     *
     * @param startUrls  стартовые URL
     * @param maxPages   максимум страниц для обхода
     * @return количество обработанных страниц
     */
    public int startCrawler(List<String> startUrls, int maxPages) {
        log.info("Crawler started: urls={}, maxPages={}", startUrls.size(), maxPages);

        BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>(startUrls);
        Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
        AtomicInteger pagesProcessed = new AtomicInteger(0);

        int workers = Math.min(urlQueue.size(), 4);
        if (workers == 0) {
            log.warn("No URLs to crawler");
            return 0;
        }

        CountDownLatch latch = new CountDownLatch(workers);

        for (int i = 0; i < workers; i++) {
            CrawlerWorker worker = new CrawlerWorker(
                    latch,
                    urlQueue,
                    visitedUrls,
                    repository,
                    htmlParser,
                    fileLogger,
                    pagesProcessed,
                    maxPages
            );
            crawlerPool.submit(worker);
        }

        try {
            boolean finished = latch.await(2, TimeUnit.MINUTES);
            if (!finished) {
                log.warn("Crawler timed out after 2 minutes!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while waiting for crawler completion");
        }

        int result = pagesProcessed.get();
        log.info("Crawler finished: pages processed = {}", result);
        fileLogger.log("Crawler finished: pages processed = " + result);

        return result;
    }
}
