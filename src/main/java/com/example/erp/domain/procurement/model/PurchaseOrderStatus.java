package com.example.erp.domain.procurement.model;

/**
 * 采购单状态。
 *
 * 状态流转：
 * DRAFT -> SUBMITTED -> CANCELLED
 * DRAFT -> CANCELLED
 */
public enum PurchaseOrderStatus {

    DRAFT,
    SUBMITTED,
    CANCELLED
}