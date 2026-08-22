package com.jono.transactionlab.controller;

import com.jono.transactionlab.execption.PaymentFailedException;
import com.jono.transactionlab.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
