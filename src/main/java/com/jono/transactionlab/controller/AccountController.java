package com.jono.transactionlab.controller;

import com.jono.transactionlab.execption.PaymentFailedException;
import com.jono.transactionlab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final InventoryService inventoryService;
    private final ExternalService externalService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final StockService stockService;

    public AccountController(
            InventoryService inventoryService, ExternalService externalService, AccountService accountService,
            OrderService orderService, StockService stockService) {
        this.inventoryService = inventoryService;
        this.externalService = externalService;
        this.accountService = accountService;
        this.orderService = orderService;
        this.stockService = stockService;
    }

    @PostMapping("/test-required")
    public String testRequired() {

        orderService.placeOrder();

        return "REQUIRED test completed";
    }

    @PostMapping("/test-requires-new")
    public String testRequiresNew() {

        orderService.placeOrder();

        return "Order completed";
    }

    @PostMapping("/test-supports-without-tx")
    public String testSupportsWithoutTransaction() {

        inventoryService.checkInventory();

        return "SUPPORTS without transaction completed";
    }

    @PostMapping("/test-mandatory")
    public String testMandatory() {

        stockService.reduceStock();

        return "Mandatory completed";
    }

    @PostMapping("/test-never")
    public String testNever() {

        externalService.performOperation();

        return "NEVER completed";
    }


    // 1. Successful Transaction
    @RequestMapping("/transfer")
    public String transfer(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam BigDecimal amount
    ) {
        accountService.transferMoney(from, to, amount);

        return "Transfer successful";
    }

    // 2. Unchecked Exception → RuntimeException → ROLLBACK
    @RequestMapping("/transfer-failure")
    public String transferFailure(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam BigDecimal amount
    ) {
        accountService.transferMoneyWithFailure(
                from,
                to,
                amount
        );

        return "This should never execute";
    }

    // 3. Checked Exception → rollbackFor → ROLLBACK
    @RequestMapping("/transfer-failure-checked")
    public String transferFailureChecked(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam BigDecimal amount
    ) throws PaymentFailedException {

        accountService.transferMoneyWithFailureCheckedException(
                from,
                to,
                amount
        );

        return "This should never execute";
    }
}
