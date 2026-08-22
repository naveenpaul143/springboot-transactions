package com.jono.transactionlab.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class PaymentService {

    @Transactional
    public void processPayment(){
        System.out.println("PaymentServices: processPayment()");
        System.out.println(
                "Thread: "
                        + Thread.currentThread().getName()
        );
        System.out.println("PaymentService transaction active: "+ TransactionSynchronizationManager.isActualTransactionActive());

        /* Required rollback
        throw new RuntimeException(
                "Payment failed"
        );

         */


    }
}
