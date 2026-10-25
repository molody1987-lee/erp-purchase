package com.example.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.erp.infrastructure.procurement.persistence.mapper")
public class ErpPurchaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpPurchaseApplication.class, args);
    }
}