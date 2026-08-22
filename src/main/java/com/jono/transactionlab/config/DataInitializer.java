package com.jono.transactionlab.config;

import com.jono.transactionlab.entity.Account;
import com.jono.transactionlab.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(AccountRepository accountRepository){
        return args -> {

            if(accountRepository.count()==0) {
                accountRepository.save(new Account("Naveen", new BigDecimal("10000"))
                );
                accountRepository.save(new Account("Singh", new BigDecimal("5000"))
                );
            }
        };
    }
}
