package com.webcrawler.service;

import com.webcrawler.dto.ParsedPage;
import com.webcrawler.entity.Contact;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HtmlParser {

    public ParsedPage parse(String sourceUrl, String html) {
        Document document = Jsoup.parse(html, sourceUrl);
        List<String> links = extractLinks(document);
        List<Contact> contacts = extractContacts(sourceUrl, document);
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

    private List<Contact> extractContacts(String sourceUrl, Document document) {
        String companyName = null;

        Element ogSiteName = document.selectFirst("meta[property=og:site_name]");
        if (ogSiteName != null && !ogSiteName.attr("content").isBlank()) {
            companyName = ogSiteName.attr("content").trim();
        }
        else if (!document.title().isBlank()) {
            String rawTitle = document.title().trim();
            companyName = rawTitle.split("[:|—\\-]")[0].trim();
        }
        else {
            companyName = extractDomain(sourceUrl);
        }

        String text = document.body().text();
        List<String> emails = findMatches(RegexPatterns.EMAIL, text);
        List<String> phones = findMatches(RegexPatterns.PHONE, text);
        List<String> addresses = findMatches(RegexPatterns.ADDRESS, text);

        if (emails.isEmpty() && phones.isEmpty() && addresses.isEmpty()) {
            return List.of();
        }

        Contact contact = new Contact();
        contact.setCompanyName(companyName);
        contact.setSourceUrl(sourceUrl);

        if (!emails.isEmpty()) contact.setEmail(String.join(", ", emails));
        if (!phones.isEmpty()) contact.setPhone(String.join(", ", phones));
        if (!addresses.isEmpty()) contact.setAddress(String.join(", ", addresses));

        return List.of(contact);
    }

    private List<String> findMatches(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group().trim();
            if (!matches.contains(match)) {
                matches.add(match);
            }
        }
        return matches;
    }

    private String extractDomain(String url) {
        try {
            String host = URI.create(url).getHost();
            if (host != null) {
                if (host.startsWith("www.")) {
                    return host.substring(4);
                }
                return host;
            }
        } catch (Exception ignored) {
        }
        return "unknown";
    }
}
