package com.example.erp.interfaces.procurement.response;

import com.example.erp.application.procurement.query.PurchaseOrderView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购单详情响应。
 */
public record PurchaseOrderResponse(
        Long id,
        String orderNo,
        String supplierName,
        String status,
        BigDecimal totalAmount,
        List<Item> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public record Item(
            Long productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal amount
    ) {
    }

    public static PurchaseOrderResponse from(PurchaseOrderView view) {
        List<Item> items = view.items().stream()
                .map(i -> new Item(i.productId(), i.productName(), i.quantity(), i.unitPrice(), i.amount()))
                .toList();
        return new PurchaseOrderResponse(
                view.id(),
                view.orderNo(),
                view.supplierName(),
                view.status(),
                view.totalAmount(),
                items,
                view.createdAt(),
                view.updatedAt());
    }
}