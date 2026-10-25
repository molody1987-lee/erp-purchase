package com.example.erp.application.procurement.command;

import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.model.PurchaseOrderItem;
import com.example.erp.domain.procurement.model.PurchaseOrderStatus;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletePurchaseOrderHandlerTest {

    @Mock
    private PurchaseOrderRepository repository;

    @InjectMocks
    private DeletePurchaseOrderHandler handler;

    @Test
    void 草稿采购单可以删除() {
        PurchaseOrder draft = PurchaseOrder.reconstruct(
                1L, "PO1001", "供应商A", PurchaseOrderStatus.DRAFT,
                List.of(new PurchaseOrderItem(1L, "商品A", 1, BigDecimal.TEN)),
                LocalDateTime.now(), LocalDateTime.now(), 0);
        when(repository.findById(1L)).thenReturn(Optional.of(draft));

        handler.handle(new DeletePurchaseOrderCommand(1L));

        verify(repository).delete(1L);
    }
}