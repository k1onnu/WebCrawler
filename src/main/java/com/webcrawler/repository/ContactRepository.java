package com.webcrawler.repository;

import com.webcrawler.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByEmail(String email);

    List<Contact> findAllByEmailIn(Set<String> emails);
}
