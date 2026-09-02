package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawalService {
    private final AccountRepository accountRepository;

    public WithdrawalService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount){
        System.out.println(
                "================================"
        );

        System.out.println(
                "Transaction started: "
                        + Thread.currentThread().getName()
        );

       // Use for optimistic locking => Account account = accountRepository.findByIdForUpdate(accountId).orElseThrow();
       //for pesimistic locking
        Account account = accountRepository.findById(accountId).orElseThrow();
        System.out.println(
                "Balance READ = "
                        + account.getBalance()
        );

        // Check balance
        if (account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException(
                    "Insufficient balance"
            );
        }

        // Make both transactions overlap
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);

        account.setBalance(newBalance);

        accountRepository.save(account);

        System.out.println(
                "Balance WRITTEN = "
                        + newBalance
        );

        System.out.println(
                "Transaction completed"
        );


    }

}
