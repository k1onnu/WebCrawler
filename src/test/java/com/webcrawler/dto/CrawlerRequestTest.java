package com.webcrawler.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void shouldBeValidForCorrectData() {
        CrawlerRequest request = new CrawlerRequest(
                List.of("https://example.com"), 10);

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Валидный запрос не должен иметь ошибок");
    }

    @Test
    void shouldFailWhenMaxPagesIsZero() {
        CrawlerRequest request = new CrawlerRequest(
                List.of("https://example.com"), 0);

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("Минимум 1 страница", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenUrlsEmpty() {
        CrawlerRequest request = new CrawlerRequest(List.of(), 10);

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("Список URL не должен быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenUrlBlank() {
        CrawlerRequest request = new CrawlerRequest(List.of("  "), 10);

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals("URL не может быть пустым", violations.iterator().next().getMessage());
    }
}