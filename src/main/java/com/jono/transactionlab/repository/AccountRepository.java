package com.jono.transactionlab.repository;

import com.jono.transactionlab.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
