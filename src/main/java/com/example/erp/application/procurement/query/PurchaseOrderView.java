package com.example.erp.application.procurement.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购单详情读模型（面向查询场景，无需加载完整聚合）。
 */
public record PurchaseOrderView(
        Long id,
        String orderNo,
        String supplierName,
        String status,
        BigDecimal totalAmount,
        List<PurchaseOrderItemView> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}