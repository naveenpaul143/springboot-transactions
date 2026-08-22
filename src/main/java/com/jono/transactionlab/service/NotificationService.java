package com.jono.transactionlab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class NotificationService {

    @Transactional(
            propagation = Propagation.NOT_SUPPORTED
    )
    public void sendNotification() {

        boolean transactionActive =
                TransactionSynchronizationManager
                        .isActualTransactionActive();

        System.out.println(
                "NotificationService transaction active: "
                        + transactionActive
        );
    }
}