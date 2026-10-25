package com.example.erp.interfaces.procurement.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 创建采购单请求。
 */
public record CreatePurchaseOrderRequest(
        @NotBlank(message = "供应商不能为空")
        String supplierName,

        @NotNull(message = "采购明细不能为空")
        @NotEmpty(message = "采购明细不能为空")
        @Valid
        List<PurchaseOrderItemRequest> items
) {
}