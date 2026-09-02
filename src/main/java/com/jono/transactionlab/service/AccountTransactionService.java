package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountTransactionService {

    private final AccountRepository accountRepository;

    public AccountTransactionService(
            AccountRepository accountRepository) {

        this.accountRepository =
                accountRepository;
    }

    @Transactional
    public void updateBalance(
            Long id,
            BigDecimal amount) {

        Account account =
                accountRepository.findById(id)
                        .orElseThrow();

        System.out.println(
                "READ Balance: "
                        + account.getBalance()
        );

        System.out.println(
                "READ Version: "
                        + account.getVersion()
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        account.setBalance(
                account.getBalance()
                        .add(amount)
        );

        accountRepository.save(account);

        System.out.println(
                "Update Success"
        );
    }
}