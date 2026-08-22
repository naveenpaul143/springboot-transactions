package com.jono.transactionlab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ExternalService {

    @Transactional(propagation = Propagation.NEVER)
    public void performOperation() {

        System.out.println(
                "ExternalService transaction active: "
                        + TransactionSynchronizationManager
                        .isActualTransactionActive()
        );

        System.out.println(
                "External operation executed"
        );
    }
}