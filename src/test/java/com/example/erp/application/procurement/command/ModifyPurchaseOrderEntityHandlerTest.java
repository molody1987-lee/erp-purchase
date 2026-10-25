package com.example.erp.application.procurement.command;

import com.example.erp.domain.procurement.model.PurchaseOrderEntity;
import com.example.erp.domain.procurement.model.PurchaseOrderItem;
import com.example.erp.domain.procurement.model.PurchaseOrderStatus;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModifyPurchaseOrderEntityHandlerTest {

    @Mock
    private PurchaseOrderRepository repository;

    @InjectMocks
    private ModifyPurchaseOrderHandler handler;

    @Test
    void 草稿采购单可以修改() {
        PurchaseOrderEntity draft = PurchaseOrderEntity.reconstruct(
                1L, "PO1001", "供应商A", PurchaseOrderStatus.DRAFT,
                List.of(new PurchaseOrderItem(1L, "商品A", 1, BigDecimal.TEN)),
                LocalDateTime.now(), LocalDateTime.now(), 0);
        when(repository.findById(1L)).thenReturn(Optional.of(draft));

        handler.handle(new ModifyPurchaseOrderCommand(
                1L, "供应商B",
                List.of(new PurchaseOrderItemCommand(2L, "商品B", 3, new BigDecimal("5.00")))));

        ArgumentCaptor<PurchaseOrderEntity> captor = ArgumentCaptor.forClass(PurchaseOrderEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getSupplierName()).isEqualTo("供应商B");
        assertThat(captor.getValue().getItems()).hasSize(1);
        assertThat(captor.getValue().getItems().get(0).getProductName()).isEqualTo("商品B");
    }
}