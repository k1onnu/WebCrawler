package com.webcrawler.service;

import com.webcrawler.dto.ParsedPage;
import com.webcrawler.entity.Contact;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HtmlParserTest {
    private HtmlParser parser = new HtmlParser();

    @Test
    void shouldExtractEmailFromHtml() {
        String html = """
                <html>
                  <body>
                    <h1>Контакты</h1>
                    <p>Свяжитесь с нами: <a href="mailto:info@pupupu.ru">info@pupupu.ru</a></p>
                    <p>Отдел продаж: sales@pupupu.ru</p>
                  </body>
                </html>
                """;

        ParsedPage result = parser.parse("https://pupupu.ru/contacts", html);

        List<Contact> contacts = result.contacts();
        assertEquals(1, contacts.size(), "Должен быть ровно 1 Contact");

        String email = contacts.get(0).getEmail();

        assertNotNull(email, "Email не должен быть null");
        assertTrue(email.contains("info@pupupu.ru"),
                "Должен быть найден info@pupupu.ru");
        assertTrue(email.contains("sales@pupupu.ru"),
                "Должен быть найден sales@pupupu.ru");;
    }

    @Test
    void shouldExtractLinksFromHtml() {
        String html = """
                <html>
                  <body>
                    <a href="/about">О нас</a>
                    <a href="https://example.com/page">Внешняя</a>
                    <a href="/contacts">Контакты</a>
                    <a>Без href — не должна попасть</a>
                  </body>
                </html>
                """;

        ParsedPage result = parser.parse("https://pupupu.ru/", html);
        List<String> links = result.links();

        assertEquals(3, links.size(), "Должно быть 3 ссылки");

        // Проверяем, что относительные ссылки стали абсолютными
        assertTrue(links.contains("https://pupupu.ru/about"),
                "Относительная /about должна стать абсолютной");
        assertTrue(links.contains("https://example.com/page"),
                "Внешняя ссылка должна остаться");
        assertTrue(links.contains("https://pupupu.ru/contacts"),
                "Относительная /contacts должна стать абсолютной");
    }

    @Test
    void shouldReturnEmptyContactsWhenNoEmails() {
        String html = """
                <html>
                  <body>
                    <p>Тут нет контактов, только текст.</p>
                  </body>
                </html>
                """;

        ParsedPage result = parser.parse("https://test.ru", html);

        assertTrue(result.contacts().isEmpty(),
                "Контактов быть не должно. D HTML нет email");
    }
}
