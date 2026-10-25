package com.example.erp.application.procurement.command;

import java.util.List;

/**
 * 创建采购单命令。
 */
public record CreatePurchaseOrderCommand(
        String supplierName,
        List<PurchaseOrderItemCommand> items
) {
}