package com.example.erp.application.procurement.query;

import java.util.Optional;

/**
 * 采购单查询仓储接口，定义在 Application（读侧），实现位于 Infrastructure。
 */
public interface PurchaseOrderQueryRepository {

    Optional<PurchaseOrderView> findById(Long id);
}