package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class IsolationTestService {

    private final AccountRepository accountRepository;
    private final EntityManager entityManager;

    public IsolationTestService(AccountRepository accountRepository, EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void readCommittedTest(Long id) {

        System.out.println("========== TRANSACTION A STARTED ==========");

        Account account1 = accountRepository
                .findById(id)
                .orElseThrow();

        System.out.println(
                "FIRST READ BALANCE = "
                        + account1.getBalance()
        );

        System.out.println(
                "WAITING 10 SECONDS..."
        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        entityManager.clear(); //Added for Repeatable read
        Account account2 = accountRepository
                .findById(id)
                .orElseThrow();


        System.out.println(
                "SECOND READ BALANCE = "
                        + account2.getBalance()
        );

        System.out.println("========== TRANSACTION A END ==========");
    }

    @Transactional
    public void updateBalance(Long id, BigDecimal newBalance) {

        System.out.println(
                "========== TRANSACTION B STARTED =========="
        );

        Account account = accountRepository
                .findById(id)
                .orElseThrow();

        System.out.println(
                "OLD BALANCE = "
                        + account.getBalance()
        );

        account.setBalance(newBalance);

       // accountRepository.save(account);

        System.out.println(
                "BALANCE UPDATED TO = "
                        + newBalance
        );

        System.out.println(
                "========== TRANSACTION B END =========="
        );
    }

    @Transactional
    public void transactionA() {

        System.out.println(
                "========== TRANSACTION A START =========="
        );

        System.out.println(
                "A: Transaction started"
        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "A: Transaction finished"
        );

        System.out.println(
                "========== TRANSACTION A END =========="
        );
    }

    @Transactional
    public void transactionB() {

        System.out.println(
                "========== TRANSACTION B START =========="
        );

        System.out.println(
                "B: Transaction started"
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "B: Transaction finished"
        );

        System.out.println(
                "========== TRANSACTION B END =========="
        );
    }
}