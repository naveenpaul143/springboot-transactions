package com.jono.transactionlab.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class OrderService {


    private final StockService stockService;

    public OrderService(
              StockService stockService) {
        this.stockService = stockService;

    }

     /*
    private final NotificationService notificationService;

    public OrderService(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }



    private final InventoryService inventoryService;

    public OrderService(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private final PaymentService paymentService;

   //By Default REQUIRED - same transaction exists entire one
    @Transactional
    public void placeOrder(){
        System.out.println("OrderService: placeOrder()");
        System.out.println(
                "Thread: "
                        + Thread.currentThread().getName()
        );
        System.out.println("OrderService transaction active: "+ TransactionSynchronizationManager.isActualTransactionActive());

        paymentService.processPayment();
        System.out.println("OrderService: order completed");

    }




    //now -> Propagation.REQUIRES_NEW [Suspend old transaction & start new transaction]
    private final AuditService auditService;

    public OrderService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Transactional
    public void placeOrder(){
        System.out.println("OrderService transaction started");

        auditService.saveAudit("ORDER_CREATED");

        System.out.println(
                "OrderService: throwing exception"
        );

        throw new RuntimeException(
                "Order creation failed"
        );

    }




    //now -> Propagation.SUPPORTS [ If a transaction exists → join it. If no transaction exists → execute without a transaction.]
    @Transactional
    public void placeOrder() {

        System.out.println(
                "OrderService transaction active: "
                        + TransactionSynchronizationManager
                        .isActualTransactionActive()
        );

        inventoryService.checkInventory();

        System.out.println(
                "OrderService completed"
        );
    }


    //NOT_SUPPORTED: Execute without a transaction. If a transaction already exists, suspend it.
    @Transactional
public void placeOrder() {

    System.out.println(
            "OrderService transaction active: "
                    + TransactionSynchronizationManager
                    .isActualTransactionActive()
    );

    notificationService.sendNotification();

    System.out.println(
            "OrderService completed"
    );


}


     */

    //Mandatory - "I refuse to work unless someone else has started a transaction."

    @Transactional
    public void placeOrder() {

        System.out.println(
                "OrderService transaction active: "
                        + TransactionSynchronizationManager
                        .isActualTransactionActive()
        );

        stockService.reduceStock();

        System.out.println("Order completed");
    }





/*
We need to see whether both methods are running inside the same transaction.
TransactionSynchronizationManager
 */

}
