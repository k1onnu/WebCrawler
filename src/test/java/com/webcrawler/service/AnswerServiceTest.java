package com.webcrawler.service;

import com.webcrawler.dto.ContactDto;
import com.webcrawler.entity.Contact;
import com.webcrawler.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private ContactRepository repository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    void shouldSortByEmailAndApplyPagination() {
        Contact c1 = new Contact(1L, "B Company", "b@site.ru", null, null, "url1");
        Contact c2 = new Contact(2L, "A Company", "a@site.ru", null, null, "url2");
        Contact c3 = new Contact(3L, "C Company", "c@site.ru", null, null, "url3");

        when(repository.findAll()).thenReturn(List.of(c1, c2, c3));

        List<ContactDto> result = answerService.getContacts(0, 2, "email");

        assertEquals(2, result.size());
        assertEquals("a@site.ru", result.get(0).email());
        assertEquals("b@site.ru", result.get(1).email());
    }

    @Test
    void shouldReturnSecondPage() {
        Contact c1 = new Contact(1L, "A", "a@site.ru", null, null, "url1");
        Contact c2 = new Contact(2L, "B", "b@site.ru", null, null, "url2");
        Contact c3 = new Contact(3L, "C", "c@site.ru", null, null, "url3");

        when(repository.findAll()).thenReturn(List.of(c1, c2, c3));

        List<ContactDto> result = answerService.getContacts(1, 2, "email");

        assertEquals(1, result.size());
        assertEquals("c@site.ru", result.get(0).email());
    }

    @Test
    void shouldReturnEmptyWhenNoContacts() {
        when(repository.findAll()).thenReturn(List.of());

        List<ContactDto> result = answerService.getContacts(0, 10, "email");

        assertTrue(result.isEmpty());
    }
}
