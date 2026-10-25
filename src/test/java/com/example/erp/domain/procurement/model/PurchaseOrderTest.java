package com.example.erp.domain.procurement.model;

import com.example.erp.domain.procurement.exception.PurchaseOrderStatusException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseOrderTest {

    private PurchaseOrder draftOrder() {
        return PurchaseOrder.create("PO001", "供应商A",
                List.of(new PurchaseOrderItem(1L, "商品A", 2, new BigDecimal("10.00"))));
    }

    @Test
    void 创建采购单应为草稿状态() {
        PurchaseOrder order = draftOrder();

        assertThat(order.getStatus()).isEqualTo(PurchaseOrderStatus.DRAFT);
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.totalAmount()).isEqualByComparingTo(new BigDecimal("20.00"));
    }

    @Test
    void 草稿采购单可以提交() {
        PurchaseOrder order = draftOrder();

        order.submit();

        assertThat(order.getStatus()).isEqualTo(PurchaseOrderStatus.SUBMITTED);
    }

    @Test
    void 已提交采购单可以取消() {
        PurchaseOrder order = draftOrder();
        order.submit();

        order.cancel();

        assertThat(order.getStatus()).isEqualTo(PurchaseOrderStatus.CANCELLED);
    }

    @Test
    void 已提交采购单不能修改() {
        PurchaseOrder order = draftOrder();
        order.submit();

        assertThatThrownBy(() -> order.modify("供应商B",
                List.of(new PurchaseOrderItem(2L, "商品B", 1, BigDecimal.ONE))))
                .isInstanceOf(PurchaseOrderStatusException.class);
    }

    @Test
    void 已取消采购单不能提交() {
        PurchaseOrder order = draftOrder();
        order.cancel();

        assertThatThrownBy(order::submit)
                .isInstanceOf(PurchaseOrderStatusException.class);
    }

    @Test
    void 已取消采购单不能重复取消() {
        PurchaseOrder order = draftOrder();
        order.cancel();

        assertThatThrownBy(order::cancel)
                .isInstanceOf(PurchaseOrderStatusException.class);
    }

    @Test
    void 已提交采购单不能删除() {
        PurchaseOrder order = draftOrder();
        order.submit();

        assertThatThrownBy(order::delete)
                .isInstanceOf(PurchaseOrderStatusException.class);
    }

    @Test
    void 草稿采购单可以删除() {
        PurchaseOrder order = draftOrder();

        order.delete();
    }

    @Test
    void 空明细采购单不能创建() {
        assertThatThrownBy(() -> PurchaseOrder.create("PO002", "供应商A", List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}