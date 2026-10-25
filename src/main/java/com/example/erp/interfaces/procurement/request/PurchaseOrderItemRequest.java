package com.example.erp.interfaces.procurement.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * 采购明细请求。
 */
public record PurchaseOrderItemRequest(
        Long productId,

        @NotBlank(message = "商品名称不能为空")
        String productName,

        @NotNull(message = "采购数量不能为空")
        @Positive(message = "采购数量必须大于0")
        Integer quantity,

        @NotNull(message = "采购单价不能为空")
        @PositiveOrZero(message = "采购单价不能为负")
        BigDecimal unitPrice
) {
}