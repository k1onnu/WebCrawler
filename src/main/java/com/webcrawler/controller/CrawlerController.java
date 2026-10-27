package com.webcrawler.controller;

import com.webcrawler.dto.CrawlerRequest;
import com.webcrawler.service.CrawlerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerService crawlerService;

    @PostMapping("/crawler")
    public ResponseEntity<Map<String, Object>> startCrawler(@Valid @RequestBody CrawlerRequest request) {
        int pagesProcessed = crawlerService.startCrawler(
                request.startUrls(),
                request.maxPages()
        );

        return ResponseEntity.ok(Map.of(
                "pagesProcessed", pagesProcessed,
                "message", "Crawler finished"
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValiadtionException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }
}
