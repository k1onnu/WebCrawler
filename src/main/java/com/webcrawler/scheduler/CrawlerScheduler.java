package com.webcrawler.scheduler;

import com.webcrawler.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerScheduler {

    private final CrawlerService crawlerService;

    @Scheduled(fixedDelayString = "${crawler.schedule.delay:600000}")
    public void scheduledCrawl() {
        log.info("Scheduled crawler started");

        crawlerService.startCrawler(List.of("https://skillbox.ru/"), 5);

        log.info("Scheduled crawler finished");
    }
}