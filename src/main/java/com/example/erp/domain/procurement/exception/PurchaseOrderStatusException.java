package com.example.erp.domain.procurement.exception;

import com.example.erp.domain.shared.exception.DomainException;

/**
 * 采购单状态不合法（非法状态流转）。
 */
public class PurchaseOrderStatusException extends DomainException {

    public PurchaseOrderStatusException(String message) {
        super(message);
    }
}