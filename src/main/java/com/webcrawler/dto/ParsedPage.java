package com.webcrawler.dto;

import com.webcrawler.entity.Contact;
import java.util.List;

public record ParsedPage(
        List<Contact> contacts,
        List<String> links
) {
}
