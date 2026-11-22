package com.webcrawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_source_url", columnList = "source_url")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String companyName;

    @Column(length = 2000)
    private String email;

    @Column(length = 500)
    private String phone;

    @Column(length = 2000)
    private String address;

    @Column(length = 500)
    private String sourceUrl;
}
