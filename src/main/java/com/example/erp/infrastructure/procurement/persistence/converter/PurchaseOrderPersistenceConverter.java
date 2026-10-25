package com.example.erp.infrastructure.procurement.persistence.converter;

import com.example.erp.domain.procurement.model.PurchaseOrderEntity;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 采购单领域模型与持久化对象的结构性转换。
 * 明细（items）由仓储层单独组装，此处不参与映射。
 */
@Mapper(componentModel = "spring")
public interface PurchaseOrderPersistenceConverter {

    @Mapping(target = "deleted", ignore = true)
    PurchaseOrderPO toPO(PurchaseOrderEntity order);
}