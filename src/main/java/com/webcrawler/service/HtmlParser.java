package com.webcrawler.service;

import com.webcrawler.dto.ParsedPage;
import com.webcrawler.entity.Contact;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    private static final String EMAIL_REGEX = "[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public ParsedPage parse(String sourceUrl, String html) {
        Document document = Jsoup.parse(html, sourceUrl);
        List<String> links = extractLinks(document);
        List<Contact> contacts = extractEmails(sourceUrl, document);
        return new ParsedPage(contacts, links);
    }

    private List<String> extractLinks(Document document) {
        List<String> result = new ArrayList<>();
        Elements anchors = document.select("a[href]");

        for (Element a : anchors) {
            String href = a.absUrl("href");
            if (!href.isBlank()) {
                result.add(href);
            }
        }
        return result;
    }

    private List<Contact> extractEmails(String sourceUrl, Document document) {
        String text = document.body().text();
        Matcher matcher = EMAIL_PATTERN.matcher(text);
        List<String> emails = new ArrayList<>();

        while (matcher.find()) {
            String email = matcher.group();
            if(!emails.contains(email)) {
                emails.add(email);
            }
        }

        if (emails.isEmpty()) {
            return List.of();
        }

        Contact contact = new Contact();
        contact.setEmail(String.join(", ", emails));
        contact.setSourceUrl(sourceUrl);

        return List.of(contact);
    }
}
