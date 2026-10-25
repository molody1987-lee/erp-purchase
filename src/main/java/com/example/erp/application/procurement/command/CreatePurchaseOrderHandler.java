package com.example.erp.application.procurement.command;

import com.example.erp.application.procurement.OrderNoGenerator;
import com.example.erp.application.procurement.result.CreatePurchaseOrderResult;
import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.model.PurchaseOrderItem;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreatePurchaseOrderHandler {

    private final PurchaseOrderRepository repository;
    private final OrderNoGenerator orderNoGenerator;

    @Transactional
    public CreatePurchaseOrderResult handle(CreatePurchaseOrderCommand command) {
        List<PurchaseOrderItem> items = command.items().stream()
                .map(c -> new PurchaseOrderItem(c.productId(), c.productName(), c.quantity(), c.unitPrice()))
                .toList();

        PurchaseOrder order = PurchaseOrder.create(
                orderNoGenerator.next(), command.supplierName(), items);

        Long id = repository.save(order);
        return new CreatePurchaseOrderResult(id, order.getOrderNo(), order.getStatus().name());
    }
}