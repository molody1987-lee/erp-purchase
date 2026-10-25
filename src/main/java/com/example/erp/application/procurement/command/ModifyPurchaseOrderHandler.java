package com.example.erp.application.procurement.command;

import com.example.erp.domain.procurement.exception.PurchaseOrderNotFoundException;
import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.model.PurchaseOrderItem;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModifyPurchaseOrderHandler {

    private final PurchaseOrderRepository repository;

    @Transactional
    public void handle(ModifyPurchaseOrderCommand command) {
        PurchaseOrder order = repository.findById(command.id())
                .orElseThrow(() -> new PurchaseOrderNotFoundException(command.id()));

        List<PurchaseOrderItem> items = command.items().stream()
                .map(c -> new PurchaseOrderItem(c.productId(), c.productName(), c.quantity(), c.unitPrice()))
                .toList();

        order.modify(command.supplierName(), items);
        repository.save(order);
    }
}