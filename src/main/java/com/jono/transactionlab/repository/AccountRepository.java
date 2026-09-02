package com.jono.transactionlab.repository;

import com.jono.transactionlab.dto.AccountSummaryDTO;
import com.jono.transactionlab.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
      @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("""
           SELECT a
           FROM Account a
           WHERE a.id = :id
           """)
        Optional<Account> findByIdForUpdate(
                @Param("id") Long id
        );

    @Query("""
    SELECT a
    FROM Account a
    WHERE a.balance > :amount
""")
    List<Account> findAccountsWithBalanceGreaterThan(
            @Param("amount") BigDecimal amount
    );

    @Query("""
    SELECT a
    FROM Account a
    WHERE a.accountHolderName LIKE %:name%
""")
    List<Account> searchByAccountHolderName(
            @Param("name") String name
    );

    @Query("""
    SELECT a
    FROM Account a
    WHERE a.balance BETWEEN :min AND :max
""")
    List<Account> findAccountsByBalanceRange(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max
    );

    @Query("""
    SELECT a
    FROM Account a
    WHERE a.balance > :amount
    ORDER BY a.balance DESC
""")
    List<Account> findRichAccountsSorted(
            @Param("amount") BigDecimal amount
    );


    @Query("""
    SELECT a
    FROM Account a
    WHERE a.id IN :ids
""")
    List<Account> findAccountsByIds(
            @Param("ids") List<Long> ids
    );


    @Query("""
        SELECT COUNT(a) from Account a where a.balance>:amount
""")
    long countAccountsWithBalanceGreaterThan(
            @Param("amount") BigDecimal amount
    );

    @Query("""
    SELECT SUM(a.balance)
    FROM Account a
""")
    BigDecimal getTotalBalance();
    @Query("""
    SELECT AVG(a.balance)
    FROM Account a
""")
    Double getAverageBalance();


    @Query("""
    SELECT a.accountHolderName, COUNT(a)
    FROM Account a
    GROUP BY a.accountHolderName
""")
    List<Object[]> countAccountsByHolderName();

    @Query("""
    SELECT a.accountHolderName, COUNT(a)
    FROM Account a
    GROUP BY a.accountHolderName
    HAVING COUNT(a) >= 2
""")
    List<Object[]> findHoldersWithMultipleAccounts();

    @Query("""
    SELECT new com.jono.transactionlab.dto.AccountSummaryDTO(
        a.id,
        a.accountHolderName,
        a.balance
    )
    FROM Account a
    WHERE a.balance > :amount
""")
    List<AccountSummaryDTO> findAccountSummaries(
            @Param("amount") BigDecimal amount
    );


    }


