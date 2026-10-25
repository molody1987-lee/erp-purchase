package com.example.erp.domain.procurement.model;

import java.math.BigDecimal;

/**
 * 采购单明细（Entity），隶属于采购单聚合。
 */
public class PurchaseOrderItem {

    private final Long productId;
    private final String productName;
    private final Integer quantity;
    private final BigDecimal unitPrice;

    protected PurchaseOrderItem() {
        this.productId = null;
        this.productName = null;
        this.quantity = null;
        this.unitPrice = null;
    }

    public PurchaseOrderItem(Long productId, String productName, Integer quantity, BigDecimal unitPrice) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("采购数量必须大于0");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("采购单价不能为负");
        }
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal amount() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}