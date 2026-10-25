package com.example.erp.infrastructure.procurement.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.erp.application.procurement.query.PurchaseOrderItemView;
import com.example.erp.application.procurement.query.PurchaseOrderQueryRepository;
import com.example.erp.application.procurement.query.PurchaseOrderView;
import com.example.erp.infrastructure.procurement.persistence.mapper.PurchaseOrderItemMapper;
import com.example.erp.infrastructure.procurement.persistence.mapper.PurchaseOrderMapper;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderItemPO;
import com.example.erp.infrastructure.procurement.persistence.po.PurchaseOrderPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderQueryRepositoryImpl implements PurchaseOrderQueryRepository {

    private final PurchaseOrderMapper orderMapper;
    private final PurchaseOrderItemMapper itemMapper;

    @Override
    public Optional<PurchaseOrderView> findById(Long id) {
        PurchaseOrderPO po = orderMapper.selectById(id);
        if (po == null) {
            return Optional.empty();
        }

        List<PurchaseOrderItemPO> itemPOs = itemMapper.selectList(
                new LambdaQueryWrapper<PurchaseOrderItemPO>()
                        .eq(PurchaseOrderItemPO::getOrderId, id));

        List<PurchaseOrderItemView> items = itemPOs.stream()
                .map(this::toView)
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(PurchaseOrderItemView::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PurchaseOrderView view = new PurchaseOrderView(
                po.getId(),
                po.getOrderNo(),
                po.getSupplierName(),
                po.getStatus(),
                totalAmount,
                items,
                po.getCreatedAt(),
                po.getUpdatedAt());

        return Optional.of(view);
    }

    private PurchaseOrderItemView toView(PurchaseOrderItemPO po) {
        BigDecimal amount = po.getUnitPrice().multiply(BigDecimal.valueOf(po.getQuantity()));
        return new PurchaseOrderItemView(
                po.getProductId(),
                po.getProductName(),
                po.getQuantity(),
                po.getUnitPrice(),
                amount);
    }
}