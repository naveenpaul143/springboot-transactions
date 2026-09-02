package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;

@Service
public class DeadlockService {
    private final AccountRepository accountRepository;

    public DeadlockService(
            AccountRepository accountRepository) {

        this.accountRepository = accountRepository;

    }

    @Transactional
    public void transferOneToTwo() throws InterruptedException {
        System.out.println(
                "========== TRANSACTION A =========="
        );

        //Lock Account 1
        Account account1 = accountRepository.findByIdForUpdate(1L).orElseThrow();


        System.out.println(
                "A: Account 1 LOCKED 🔒"
        );

        sleep(5000);

        // Try to lock Account 2
        Account account2 =
                accountRepository
                        .findByIdForUpdate(2L)
                        .orElseThrow();

        System.out.println(
                "A: Account 2 LOCKED 🔒"
        );

        account1.setBalance(
                account1.getBalance().subtract(BigDecimal.valueOf(100L))
        );

        account2.setBalance(
                account1.getBalance().add(BigDecimal.valueOf(100L))
        );


    }
   /*
    @Transactional
    public void transferTwoToOne() {

        System.out.println(
                "========== TRANSACTION B =========="
        );

        // Lock Account 2
        Account account2 =
                accountRepository
                        .findByIdForUpdate(2L)
                        .orElseThrow();

        System.out.println(
                "B: Account 2 LOCKED 🔒"
        );

        sleep(5000);

        // Try to lock Account 1
        Account account1 =
                accountRepository
                        .findByIdForUpdate(1L)
                        .orElseThrow();

        System.out.println(
                "B: Account 1 LOCKED 🔒"
        );

        account2.setBalance(
                account2.getBalance().subtract(BigDecimal.valueOf(200)));

        account1.setBalance(
                account1.getBalance().add(BigDecimal.valueOf(200)));


        System.out.println(
                "B: Transfer completed"
        );
    }

    */


    @Transactional
    public void transferTwoToOne() {

        System.out.println(
                "========== TRANSACTION B =========="
        );

        // Lock Account 2
        Account account1 =
                accountRepository
                        .findByIdForUpdate(1L)
                        .orElseThrow();

        System.out.println(
                "B: Account 1 LOCKED 🔒"
        );

        sleep(5000);

        // Try to lock Account 1
        Account account2 =
                accountRepository
                        .findByIdForUpdate(2L)
                        .orElseThrow();

        System.out.println(
                "B: Account 2 LOCKED 🔒"
        );

        account2.setBalance(
                account2.getBalance().subtract(BigDecimal.valueOf(200)));

        account1.setBalance(
                account1.getBalance().add(BigDecimal.valueOf(200)));


        System.out.println(
                "B: Transfer completed"
        );
    }


    private void sleep(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    }
