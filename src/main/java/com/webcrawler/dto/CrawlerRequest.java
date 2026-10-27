package com.webcrawler.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CrawlerRequest(
        @NotEmpty(message = "Список URL не должен быть пустым")
        List<@NotBlank(message = "URL не может быть пустым") String> startUrls,
        @Min(value = 1, message = "Минимум 1 страница")
        @Max(value = 50, message = "Максимум 50 страниц за раз")
        int maxPages
) {
}
