package com.jono.transactionlab.controller;

import com.jono.transactionlab.dto.AccountSummaryDTO;
import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.execption.PaymentFailedException;
import com.jono.transactionlab.service.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final InventoryService inventoryService;
    private final ExternalService externalService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final StockService stockService;
    private final IsolationTestService isolationTestService;
    private final ConcurrencyService concurrencyService;
    private final WithdrawalService withdrawalService;
    private final DeadlockService deadlockService;
    private final OptimisticRetryService optimisticRetryService;

    public AccountController(
            InventoryService inventoryService, ExternalService externalService, AccountService accountService,
            OrderService orderService, StockService stockService, IsolationTestService isolationTestService, ConcurrencyService concurrencyService, WithdrawalService withdrawalService, DeadlockService deadlockService, OptimisticRetryService optimisticRetryService) {
        this.inventoryService = inventoryService;
        this.externalService = externalService;
        this.accountService = accountService;
        this.orderService = orderService;
        this.stockService = stockService;
        this.isolationTestService = isolationTestService;
        this.concurrencyService = concurrencyService;
        this.withdrawalService = withdrawalService;
        this.deadlockService = deadlockService;
        this.optimisticRetryService = optimisticRetryService;
    }

    @GetMapping("/isolation/read-committed/{id}")
    public String readCommitted(
            @PathVariable Long id) {

        isolationTestService.readCommittedTest(id);

        return "Read committed test completed";
    }

    @PutMapping("/isolation/update/{id}/{balance}")
    public String updateBalance(
            @PathVariable Long id,
            @PathVariable BigDecimal balance) {

        isolationTestService.updateBalance(id, balance);

        return "Balance updated";
    }

    @PostMapping("/concurrency/a")
    public String transactionA() {

        isolationTestService.transactionA();

        return "Transaction A completed";
    }
    @PostMapping("/concurrency/b")
    public String transactionB() {

        isolationTestService.transactionB();

        return "Transaction B completed";
    }


    @PostMapping("/products/{id}/order")
    public String orderPizza(
            @PathVariable Long id) {

        concurrencyService.orderPizza(id);

        return "Order completed";
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


    @PostMapping(
            "/{id}/withdraw/{amount}"
    )
    public String withdraw(
            @PathVariable Long id,
            @PathVariable BigDecimal amount) {

        withdrawalService.withdraw(id, amount);

        return "Withdrawal completed";
    }

    @PostMapping("/deadlock/transfer-1-to-2")
    public String transferOneToTwo() throws InterruptedException {

        deadlockService.transferOneToTwo();

        return "Transfer 1 → 2 completed";
    }

    @PostMapping("/deadlock/transfer-2-to-1")
    public String transferTwoToOne() {

        deadlockService.transferTwoToOne();

        return "Transfer 2 → 1 completed";
    }


    @PostMapping("/retry-test/{id}/{amount}")
    public String retryTest(
            @PathVariable Long id,
            @PathVariable double amount
    ){

        optimisticRetryService.updateBalance(id,BigDecimal.valueOf(amount));
        return "Updated";
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


    @GetMapping("/balance-greater/{amount}")
    public List<Account> balanceGreater(
            @PathVariable BigDecimal amount) {

        return accountService
                .findAccountsWithBalanceGreaterThan(amount);
    }

    @GetMapping("/account-match/{name}")
    public List<Account> accountMatch(@PathVariable String name){
        return accountService.findSearchByAccountHolderName(name);

    }

    @GetMapping("/by-ids")
    public List<Account> findByIds(
            @RequestParam List<Long> ids) {

        return accountService.findAccountsByIds(ids);
    }

    @GetMapping("/count-balance-greater/{amount}")
    public long countBalanceGreater(
            @PathVariable BigDecimal amount) {

        return accountService.countAccountsWithBalanceGreaterThan(amount);
    }
    @GetMapping("/total-balance")
    public BigDecimal totalBalance() {
        return accountService.getTotalBalance();
    }
    @GetMapping("/average-balance")
    public Double averageBalance() {
        return accountService.getAverageBalance();
    }

    @GetMapping("/group-by-holder")
    public List<Object[]> groupByHolder() {
        return accountService.countAccountsByHolderName();
    }

    @GetMapping("/holders/multiple")
    public List<Object[]> findMultipleAccountHolders() {
        return accountService.findHoldersWithMultipleAccounts();
    }
    @GetMapping("/summary/{amount}")
    public List<AccountSummaryDTO> accountSummary(
            @PathVariable BigDecimal amount) {

        return accountService.findAccountSummaries(amount);
    }
}
