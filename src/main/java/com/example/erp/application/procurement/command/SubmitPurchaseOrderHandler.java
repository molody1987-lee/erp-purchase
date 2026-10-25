package com.example.erp.application.procurement.command;

import com.example.erp.domain.procurement.exception.PurchaseOrderNotFoundException;
import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SubmitPurchaseOrderHandler {

    private final PurchaseOrderRepository repository;

    @Transactional
    public void handle(SubmitPurchaseOrderCommand command) {
        PurchaseOrder order = repository.findById(command.id())
                .orElseThrow(() -> new PurchaseOrderNotFoundException(command.id()));

        order.submit();
        repository.save(order);
    }
}