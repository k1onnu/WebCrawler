package com.webcrawler.controller;

import com.webcrawler.dto.CrawlerRequest;
import com.webcrawler.service.CrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CrawlerController.class)
class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CrawlerService crawlerService;

    @Test
    void shouldStartCrawlerAndReturnResult() throws Exception {
        when(crawlerService.startCrawler(anyList(), anyInt())).thenReturn(5);

        String jsonRequest = """
                {
                    "startUrls": ["https://example.com"],
                    "maxPages": 10
                }
                """;

        mockMvc.perform(post("/api/crawler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagesProcessed").value(5))
                .andExpect(jsonPath("$.message").value("Crawler finished"));
    }

    @Test
    void shouldReturnBadRequestOnInvalidData() throws Exception {
        String jsonRequest = """
                {
                    "startUrls": [],
                    "maxPages": -5
                }
                """;

        mockMvc.perform(post("/api/crawler")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
