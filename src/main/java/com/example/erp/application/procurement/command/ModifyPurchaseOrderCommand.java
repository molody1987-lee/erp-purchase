package com.example.erp.application.procurement.command;

import java.util.List;

/**
 * 修改采购单命令。
 */
public record ModifyPurchaseOrderCommand(
        Long id,
        String supplierName,
        List<PurchaseOrderItemCommand> items
) {
}