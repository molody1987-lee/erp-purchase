package com.example.erp.application.procurement.result;

/**
 * 创建采购单结果。
 */
public record CreatePurchaseOrderResult(
        Long id,
        String orderNo,
        String status
) {
}