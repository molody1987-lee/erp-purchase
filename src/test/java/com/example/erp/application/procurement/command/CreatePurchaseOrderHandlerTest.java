package com.example.erp.application.procurement.command;

import com.example.erp.application.procurement.OrderNoGenerator;
import com.example.erp.application.procurement.result.CreatePurchaseOrderResult;
import com.example.erp.domain.procurement.model.PurchaseOrder;
import com.example.erp.domain.procurement.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePurchaseOrderHandlerTest {

    @Mock
    private PurchaseOrderRepository repository;

    @Mock
    private OrderNoGenerator orderNoGenerator;

    @InjectMocks
    private CreatePurchaseOrderHandler handler;

    @Test
    void 创建采购单应保存并返回结果() {
        when(orderNoGenerator.next()).thenReturn("PO1001");
        when(repository.save(any(PurchaseOrder.class))).thenReturn(100L);

        CreatePurchaseOrderResult result = handler.handle(new CreatePurchaseOrderCommand(
                "供应商A",
                List.of(new PurchaseOrderItemCommand(1L, "商品A", 2, new BigDecimal("10.00")))));

        assertThat(result.id()).isEqualTo(100L);
        assertThat(result.orderNo()).isEqualTo("PO1001");
        assertThat(result.status()).isEqualTo("DRAFT");

        verify(repository).save(any(PurchaseOrder.class));
    }
}