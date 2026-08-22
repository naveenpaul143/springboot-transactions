package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.execption.PaymentFailedException;
import com.jono.transactionlab.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount
    ){
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(
                ()->  new RuntimeException("From account not found")
        );

        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(
                ()->new RuntimeException("To Account not found")
        );

        //Debit
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));


        //credit
        toAccount.setBalance(toAccount.getBalance().add(amount));



    }

    @Transactional(rollbackFor = PaymentFailedException.class)
    public void transferMoneyWithFailureCheckedException(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount
    ) throws PaymentFailedException {

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() ->
                        new RuntimeException("From account not found")
                );

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() ->
                        new RuntimeException("To account not found")
                );

        // Debit
        fromAccount.setBalance(
                fromAccount.getBalance().subtract(amount)
        );

        // Intentional business failure
        throw new PaymentFailedException(
                "Payment failed intentionally"
        );

        // Credit will never execute
    }



    @Transactional
    public void transferMoneyWithFailure(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount
    ){
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow();
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow();

        //Step 1: Debit Naveen
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));

        //simulate failure
        throw new RuntimeException("Testing transaction rollback");

        // This will never execute
        // toAccount.setBalance(
        //         toAccount.getBalance().add(amount)
        // );
    }


}
