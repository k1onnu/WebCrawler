package com.webcrawler.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class CrawlerMetrics {

    private final Counter successPages;
    private final Counter errorPages;
    private final Counter savedContacts;
    @Getter
    private final Timer parseTimer;

    public CrawlerMetrics(MeterRegistry registry) {
        this.successPages = Counter.builder("crawler.pages.success")
                .description("Number of successfully processed pages")
                .register(registry);

        this.errorPages = Counter.builder("crawler.pages.error")
                .description("Number of pages with errors")
                .register(registry);

        this.savedContacts = Counter.builder("crawler.contacts.saved")
                .description("Number of saved contacts")
                .register(registry);

        this.parseTimer = Timer.builder("crawler.parse.time")
                .description("Time taken to parse a single page")
                .register(registry);
    }

    public void incrementSuccess() {
        successPages.increment();
    }

    public void incrementError() {
        errorPages.increment();
    }

    public void incrementSavedContacts(int count) {
        savedContacts.increment(count);
    }

}
