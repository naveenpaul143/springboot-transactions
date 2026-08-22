package com.jono.transactionlab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class InventoryService {

    @Transactional(propagation = Propagation.SUPPORTS)
    public void checkInventory() {

        boolean transactionActive =
                TransactionSynchronizationManager
                        .isActualTransactionActive();

        System.out.println(
                "InventoryService transaction active: "
                        + transactionActive
        );
    }
}