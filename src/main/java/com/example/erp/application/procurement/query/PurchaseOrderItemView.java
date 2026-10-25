package com.example.erp.application.procurement.query;

import java.math.BigDecimal;

/**
 * 采购明细读模型。
 */
public record PurchaseOrderItemView(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal amount
) {
}