package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import jakarta.persistence.OptimisticLockException;
import java.math.BigDecimal;
@Service
public class OptimisticRetryService {

    private final AccountTransactionService
            accountTransactionService;

    public OptimisticRetryService(
            AccountTransactionService accountTransactionService) {

        this.accountTransactionService =
                accountTransactionService;
    }

    @Retryable(
            retryFor =
                    ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(
                    delay = 1000
            )
    )
    public void updateBalance(
            Long id,
            BigDecimal amount) {

        System.out.println(
                "========== RETRY ATTEMPT =========="
        );

        accountTransactionService
                .updateBalance(id, amount);
    }
}