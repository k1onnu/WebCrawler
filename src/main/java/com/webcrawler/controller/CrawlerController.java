package com.webcrawler.controller;

import com.webcrawler.dto.CrawlerRequest;
import com.webcrawler.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerService crawlerService;

    @PostMapping("/crawler")
    public ResponseEntity<Map<String, Object>> startCrawler(@RequestBody CrawlerRequest request) {
        int pagesProcessed = crawlerService.startCrawler(
                request.startUrls(),
                request.maxPages()
        );

        return ResponseEntity.ok(Map.of(
                "pagesProcessed", pagesProcessed,
                "message", "Crawler finished"
        ));
    }
}
