package com.example.erp.domain.procurement.exception;

import com.example.erp.domain.shared.exception.DomainException;

/**
 * 采购单不存在。
 */
public class PurchaseOrderNotFoundException extends DomainException {

    public PurchaseOrderNotFoundException(Long id) {
        super("采购单不存在: id=" + id);
    }
}