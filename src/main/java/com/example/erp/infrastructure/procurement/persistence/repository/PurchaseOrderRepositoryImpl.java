package com.example.erp.infrastructure.procurement.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.model.PurchaseOrderItem;
import com.example.erp.domain.procurement.model.PurchaseOrderStatus;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import com.example.erp.infrastructure.procurement.persistence.converter.PurchaseOrderPersistenceConverter;
import com.example.erp.infrastructure.procurement.persistence.mapper.PurchaseOrderItemMapper;
import com.example.erp.infrastructure.procurement.persistence.mapper.PurchaseOrderMapper;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderItemPO;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final PurchaseOrderMapper orderMapper;
    private final PurchaseOrderItemMapper itemMapper;
    private final PurchaseOrderPersistenceConverter converter;

    @Override
    public Optional<PurchaseOrder> findById(Long id) {
        PurchaseOrderPO po = orderMapper.selectById(id);
        if (po == null) {
            return Optional.empty();
        }

        List<PurchaseOrderItemPO> itemPOs = itemMapper.selectList(
                new LambdaQueryWrapper<PurchaseOrderItemPO>()
                        .eq(PurchaseOrderItemPO::getOrderId, id));

        List<PurchaseOrderItem> items = itemPOs.stream()
                .map(this::toDomainItem)
                .toList();

        PurchaseOrder order = PurchaseOrder.reconstruct(
                po.getId(),
                po.getOrderNo(),
                po.getSupplierName(),
                PurchaseOrderStatus.valueOf(po.getStatus()),
                items,
                po.getCreatedAt(),
                po.getUpdatedAt(),
                po.getVersion());

        return Optional.of(order);
    }

    @Override
    public Long save(PurchaseOrder purchaseOrder) {
        PurchaseOrderPO po = converter.toPO(purchaseOrder);
        if (po.getId() == null) {
            orderMapper.insert(po);
        } else {
            orderMapper.updateById(po);
        }

        Long orderId = po.getId();

        // 明细采用先删后插的简化策略保证与聚合一致
        itemMapper.delete(new LambdaQueryWrapper<PurchaseOrderItemPO>()
                .eq(PurchaseOrderItemPO::getOrderId, orderId));

        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            PurchaseOrderItemPO itemPO = new PurchaseOrderItemPO();
            itemPO.setOrderId(orderId);
            itemPO.setProductId(item.getProductId());
            itemPO.setProductName(item.getProductName());
            itemPO.setQuantity(item.getQuantity());
            itemPO.setUnitPrice(item.getUnitPrice());
            itemMapper.insert(itemPO);
        }
        return orderId;
    }

    @Override
    public void delete(Long id) {
        orderMapper.deleteById(id);
        itemMapper.delete(new LambdaQueryWrapper<PurchaseOrderItemPO>()
                .eq(PurchaseOrderItemPO::getOrderId, id));
    }

    private PurchaseOrderItem toDomainItem(PurchaseOrderItemPO po) {
        return new PurchaseOrderItem(po.getProductId(), po.getProductName(), po.getQuantity(), po.getUnitPrice());
    }
}