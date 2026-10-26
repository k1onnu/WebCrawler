package com.webcrawler.service;

import java.util.regex.Pattern;

public final class RegexPatterns {

    private RegexPatterns() {}

    public static final Pattern EMAIL = Pattern.compile(
            "[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}"
    );

    public static final Pattern PHONE = Pattern.compile(
            "(\\+7|8)[\\s\\-]?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}"
    );

    public static final Pattern ADDRESS = Pattern.compile(
            "(г\\.|город)\\s+[А-Яа-я\\-]+.*?(ул\\.|улица|пр-т|проспект|д\\.|дом)\\s+.*?\\d+"
    );
}
