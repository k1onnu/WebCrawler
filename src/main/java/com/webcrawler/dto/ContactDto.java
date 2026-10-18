package com.webcrawler.dto;

public record ContactDto(
        Long id,
        String companyName,
        String email,
        String phone,
        String address,
        String sourceUrl
) {
}
