package com.example.erp.domain.procurement.model;

import com.example.erp.domain.procurement.exception.PurchaseOrderStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 采购单（Aggregate Root）。
 *
 * 领域职责：
 * 1. 维护聚合内部不变量
 * 2. 通过业务行为（create/modify/submit/cancel/delete）改变状态
 * 3. 保护状态流转规则
 */
public class PurchaseOrder {

    private Long id;
    private String orderNo;
    private String supplierName;
    private PurchaseOrderStatus status;
    private List<PurchaseOrderItem> items = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;

    protected PurchaseOrder() {
    }

    /**
     * 创建采购单，初始状态为草稿。
     */
    public static PurchaseOrder create(String orderNo, String supplierName, List<PurchaseOrderItem> items) {
        if (orderNo == null || orderNo.isBlank()) {
            throw new IllegalArgumentException("采购单号不能为空");
        }
        if (supplierName == null || supplierName.isBlank()) {
            throw new IllegalArgumentException("供应商不能为空");
        }

        PurchaseOrder order = new PurchaseOrder();
        order.orderNo = orderNo;
        order.supplierName = supplierName;
        order.replaceItems(items);
        order.status = PurchaseOrderStatus.DRAFT;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = order.createdAt;
        order.version = 0;
        return order;
    }

    /**
     * 从持久化数据重建聚合（仅限仓储层调用）。
     */
    public static PurchaseOrder reconstruct(Long id, String orderNo, String supplierName,
                                            PurchaseOrderStatus status, List<PurchaseOrderItem> items,
                                            LocalDateTime createdAt, LocalDateTime updatedAt, Integer version) {
        PurchaseOrder order = new PurchaseOrder();
        order.id = id;
        order.orderNo = orderNo;
        order.supplierName = supplierName;
        order.status = status;
        order.items = new ArrayList<>(items);
        order.createdAt = createdAt;
        order.updatedAt = updatedAt;
        order.version = version;
        return order;
    }

    /**
     * 修改采购单，仅草稿状态允许。
     */
    public void modify(String supplierName, List<PurchaseOrderItem> items) {
        ensureDraft("只有草稿状态的采购单才能修改");
        if (supplierName == null || supplierName.isBlank()) {
            throw new IllegalArgumentException("供应商不能为空");
        }
        this.supplierName = supplierName;
        this.replaceItems(items);
        this.touch();
    }

    /**
     * 提交采购单，仅草稿状态允许。
     */
    public void submit() {
        ensureDraft("只有草稿状态的采购单才能提交");
        this.status = PurchaseOrderStatus.SUBMITTED;
        this.touch();
    }

    /**
     * 取消采购单，草稿或已提交状态允许。
     */
    public void cancel() {
        if (this.status == PurchaseOrderStatus.CANCELLED) {
            throw new PurchaseOrderStatusException("采购单已取消，不能重复取消");
        }
        if (this.status == PurchaseOrderStatus.DRAFT || this.status == PurchaseOrderStatus.SUBMITTED) {
            this.status = PurchaseOrderStatus.CANCELLED;
            this.touch();
            return;
        }
        throw new PurchaseOrderStatusException("采购单当前状态不允许取消");
    }

    /**
     * 删除采购单，已提交的采购单需先取消才能删除。
     * 删除不改变领域状态，由仓储层执行逻辑删除。
     */
    public void delete() {
        if (this.status == PurchaseOrderStatus.SUBMITTED) {
            throw new PurchaseOrderStatusException("已提交的采购单不能删除，请先取消");
        }
    }

    public BigDecimal totalAmount() {
        return items.stream()
                .map(PurchaseOrderItem::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void ensureDraft(String message) {
        if (this.status != PurchaseOrderStatus.DRAFT) {
            throw new PurchaseOrderStatusException(message);
        }
    }

    private void replaceItems(List<PurchaseOrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("采购明细不能为空");
        }
        this.items = new ArrayList<>(items);
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public List<PurchaseOrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getVersion() {
        return version;
    }
}