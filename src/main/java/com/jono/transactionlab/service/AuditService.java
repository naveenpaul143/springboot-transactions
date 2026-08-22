package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.AuditLog;
import com.jono.transactionlab.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(
            AuditLogRepository auditLogRepository) {

        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    public void saveAudit(String message) {

        System.out.println(
                "AuditService transaction active: "
                        +  TransactionSynchronizationManager
                        .isActualTransactionActive()
        );

        auditLogRepository.save(
                new AuditLog(message)
        );

        System.out.println(
                "Audit saved successfully"
        );
    }
}