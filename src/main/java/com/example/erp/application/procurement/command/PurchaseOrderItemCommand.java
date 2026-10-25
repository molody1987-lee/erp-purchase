package com.example.erp.application.procurement.command;

import java.math.BigDecimal;

/**
 * 采购明细命令。
 */
public record PurchaseOrderItemCommand(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
}