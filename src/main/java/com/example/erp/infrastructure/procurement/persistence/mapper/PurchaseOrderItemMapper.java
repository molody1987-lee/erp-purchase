package com.example.erp.infrastructure.procurement.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderItemPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItemPO> {
}