package com.jono.transactionlab.repository;

import com.jono.transactionlab.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {
}