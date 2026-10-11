package com.webcrawler.concurrency;

import com.webcrawler.dto.ParsedPage;
import com.webcrawler.entity.Contact;
import com.webcrawler.repository.ContactRepository;
import com.webcrawler.service.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class CrawlerWorker implements Runnable {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final int POLL_TIMEOUT_SECONDS = 3;
    private static final int HTTP_TIMEOUT_MS = 5000;

    private final BlockingQueue<String> urlQueue;
    private final Set<String> visitedUrls;
    private final ContactRepository repository;
    private final HtmlParser htmlParser;
    private final FileLogger logger;
    private final AtomicInteger pagesProcessed;
    private final int maxPages;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        logger.log("%s started".formatted(name));

        while (true) {
            String url = pollNextUrl(name);
            if (url == null) return;

            if (!visitedUrls.add(url)) continue;

            if (pagesProcessed.incrementAndGet() > maxPages) {
                logger.log("%s reached limit.".formatted(name));
                return;
            }

            processPage(name, url);
        }
    }

    private String pollNextUrl(String name) {
        try {
            return urlQueue.poll(POLL_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log("%s interrupted, stopping".formatted(name));
            return null;
        }
    }

    private void processPage(String name, String url) {
        logger.log("%s from %s".formatted(name, url));
        try {
            Document document = Jsoup.connect(url)
                    .timeout(HTTP_TIMEOUT_MS)
                    .userAgent(USER_AGENT)
                    .get();

            ParsedPage parsedPage = htmlParser.parse(url, document.outerHtml());

            saveContacts(name, url, parsedPage.contacts());

            if(pagesProcessed.get() < maxPages)
                parsedPage.links().forEach(urlQueue::offer);

        } catch (Exception e) {
            logger.log("%s ERROR on %s: %s".formatted(name, url, e.getMessage()));
        }
    }

    private void saveContacts(String name, String url, List<Contact> contacts) {
        List<Contact> fresh = contacts.stream()
                .filter(c -> c.getEmail() != null)
                .filter(c -> !repository.existsByEmail(c.getEmail()))
                .toList();

        if (fresh.isEmpty()) return;

        repository.saveAll(fresh);;
        logger.log("%s saved %d contacts from %s".formatted(name, contacts.size(), url));
    }
}
