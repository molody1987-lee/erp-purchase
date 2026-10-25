package com.example.erp.application.procurement;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 采购单号生成器。
 */
@Component
public class OrderNoGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final AtomicInteger sequence = new AtomicInteger(0);

    public String next() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        return "PO" + timestamp + String.format("%03d", sequence.incrementAndGet() % 1000);
    }
}