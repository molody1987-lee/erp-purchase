package com.example.erp.domain.procurement.repository;

import com.example.erp.domain.procurement.model.PurchaseOrder;

import java.util.Optional;

/**
 * 采购单仓储接口，定义在 Domain，面向聚合。
 */
public interface PurchaseOrderRepository {

    Optional<PurchaseOrder> findById(Long id);

    /**
     * 保存采购单（按是否存在 id 决定插入或更新），返回持久化后的主键。
     */
    Long save(PurchaseOrder purchaseOrder);

    void delete(Long id);
}