package com.webcrawler.dto;

import java.util.List;

public record CrawlerRequest(
        List<String> startUrls,
        int maxPages
) {
}
