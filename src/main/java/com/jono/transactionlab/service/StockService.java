package com.jono.transactionlab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class StockService {

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceStock() {

        System.out.println(
                "StockService transaction active: "
                        + TransactionSynchronizationManager
                        .isActualTransactionActive()
        );

        System.out.println("Stock reduced");
    }
}