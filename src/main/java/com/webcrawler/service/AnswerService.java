package com.webcrawler.service;

import com.webcrawler.dto.ContactDto;
import com.webcrawler.entity.Contact;
import com.webcrawler.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final ContactRepository repository;

    public List<ContactDto> getContacts(int page, int size, String sortBy) {
        List<Contact> allContacts = repository.findAll();

        Comparator<Contact> comparator = switch (sortBy == null ? "id" : sortBy) {
            case "email" -> Comparator.comparing(Contact::getEmail, Comparator.nullsLast(String::compareTo));
            case "sourceUrl" -> Comparator.comparing(Contact::getSourceUrl, Comparator.nullsLast(String::compareTo));
            default -> Comparator.comparing(Contact::getId);
        };

        return allContacts.parallelStream()
                .sorted(comparator)
                .skip((long) page * size)
                .limit(size)
                .map(this::toDto)
                .toList();
    }

    private ContactDto toDto(Contact contact) {
        return new ContactDto(
                contact.getId(),
                contact.getCompanyName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getAddress(),
                contact.getSourceUrl()
        );
    }
}
