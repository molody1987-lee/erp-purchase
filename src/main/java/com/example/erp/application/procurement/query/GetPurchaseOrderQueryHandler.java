package com.example.erp.application.procurement.query;

import com.example.erp.domain.procurement.exception.PurchaseOrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrderQueryHandler {

    private final PurchaseOrderQueryRepository queryRepository;

    public PurchaseOrderView handle(GetPurchaseOrderQuery query) {
        return queryRepository.findById(query.id())
                .orElseThrow(() -> new PurchaseOrderNotFoundException(query.id()));
    }
}