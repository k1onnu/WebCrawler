package com.webcrawler.service;

import com.webcrawler.concurrency.CrawlerWorker;
import com.webcrawler.concurrency.FileLogger;
import com.webcrawler.repository.ContactRepository;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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
    private final CrawlerMetrics metrics;
    private final Tracer tracer;
    @Value("${crawler.user-agent}")
    private String userAgent;

    public int startCrawler(List<String> startUrls, int maxPages) {
        log.info("Crawler started: urls={}, maxPages={}", startUrls.size(), maxPages);

        BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>(5000);
        startUrls.forEach(urlQueue::offer);
        Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
        AtomicInteger pagesProcessed = new AtomicInteger(0);

        int workers = Math.min(urlQueue.size(), 4);
        if (workers == 0) {
            log.warn("No URLs to crawl");
            return 0;
        }

        CountDownLatch latch = new CountDownLatch(workers);

        // Список для хранения Future
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < workers; i++) {
            CrawlerWorker worker = new CrawlerWorker(
                    latch, urlQueue, visitedUrls, repository,
                    htmlParser, fileLogger, pagesProcessed, maxPages,
                    userAgent, metrics, tracer
            );
            futures.add(crawlerPool.submit(worker));
        }

        try {
            boolean finished = latch.await(2, TimeUnit.MINUTES);
            if (!finished) {
                log.warn("Crawler timed out! Cancelling tasks...");
                for (Future<?> future : futures) {
                    future.cancel(true);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while waiting for crawler completion");
            for (Future<?> future : futures) {
                future.cancel(true);
            }
        }

        int result = pagesProcessed.get();
        log.info("Crawler finished: pages processed = {}", result);
        fileLogger.log("Crawler finished: pages processed = " + result);

        return result;
    }
}
