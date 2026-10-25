package com.example.erp.interfaces.procurement;

import com.example.erp.application.procurement.command.CancelPurchaseOrderCommand;
import com.example.erp.application.procurement.command.CancelPurchaseOrderHandler;
import com.example.erp.application.procurement.command.CreatePurchaseOrderCommand;
import com.example.erp.application.procurement.command.CreatePurchaseOrderHandler;
import com.example.erp.application.procurement.command.DeletePurchaseOrderCommand;
import com.example.erp.application.procurement.command.DeletePurchaseOrderHandler;
import com.example.erp.application.procurement.command.ModifyPurchaseOrderCommand;
import com.example.erp.application.procurement.command.ModifyPurchaseOrderHandler;
import com.example.erp.application.procurement.command.PurchaseOrderItemCommand;
import com.example.erp.application.procurement.command.SubmitPurchaseOrderCommand;
import com.example.erp.application.procurement.command.SubmitPurchaseOrderHandler;
import com.example.erp.application.procurement.query.GetPurchaseOrderQuery;
import com.example.erp.application.procurement.query.GetPurchaseOrderQueryHandler;
import com.example.erp.application.procurement.query.PurchaseOrderView;
import com.example.erp.interfaces.common.ApiResponse;
import com.example.erp.interfaces.procurement.request.CreatePurchaseOrderRequest;
import com.example.erp.interfaces.procurement.request.ModifyPurchaseOrderRequest;
import com.example.erp.interfaces.procurement.request.PurchaseOrderItemRequest;
import com.example.erp.interfaces.procurement.response.CreatePurchaseOrderResponse;
import com.example.erp.interfaces.procurement.response.PurchaseOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final CreatePurchaseOrderHandler createHandler;
    private final ModifyPurchaseOrderHandler modifyHandler;
    private final SubmitPurchaseOrderHandler submitHandler;
    private final CancelPurchaseOrderHandler cancelHandler;
    private final DeletePurchaseOrderHandler deleteHandler;
    private final GetPurchaseOrderQueryHandler getQueryHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreatePurchaseOrderResponse> create(
            @RequestBody @Valid CreatePurchaseOrderRequest request) {
        CreatePurchaseOrderCommand command = new CreatePurchaseOrderCommand(
                request.supplierName(), toItemCommands(request.items()));
        return ApiResponse.success(
                CreatePurchaseOrderResponse.from(createHandler.handle(command)));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> modify(
            @PathVariable Long id,
            @RequestBody @Valid ModifyPurchaseOrderRequest request) {
        ModifyPurchaseOrderCommand command = new ModifyPurchaseOrderCommand(
                id, request.supplierName(), toItemCommands(request.items()));
        modifyHandler.handle(command);
        return ApiResponse.success();
    }

    @PostMapping("/{id}/submit")
    public ApiResponse<Void> submit(@PathVariable Long id) {
        submitHandler.handle(new SubmitPurchaseOrderCommand(id));
        return ApiResponse.success();
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<Void> cancel(@PathVariable Long id) {
        cancelHandler.handle(new CancelPurchaseOrderCommand(id));
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        deleteHandler.handle(new DeletePurchaseOrderCommand(id));
    }

    @GetMapping("/{id}")
    public ApiResponse<PurchaseOrderResponse> get(@PathVariable Long id) {
        PurchaseOrderView view = getQueryHandler.handle(new GetPurchaseOrderQuery(id));
        return ApiResponse.success(PurchaseOrderResponse.from(view));
    }

    private List<PurchaseOrderItemCommand> toItemCommands(List<PurchaseOrderItemRequest> items) {
        return items.stream()
                .map(i -> new PurchaseOrderItemCommand(
                        i.productId(), i.productName(), i.quantity(), i.unitPrice()))
                .toList();
    }
}