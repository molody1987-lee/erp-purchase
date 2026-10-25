package com.example.erp.interfaces.procurement.response;

import com.example.erp.application.procurement.result.CreatePurchaseOrderResult;

/**
 * 创建采购单响应。
 */
public record CreatePurchaseOrderResponse(
        Long id,
        String orderNo,
        String status
) {
    public static CreatePurchaseOrderResponse from(CreatePurchaseOrderResult result) {
        return new CreatePurchaseOrderResponse(result.id(), result.orderNo(), result.status());
    }
}