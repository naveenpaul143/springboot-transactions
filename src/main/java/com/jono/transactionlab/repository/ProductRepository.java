package com.jono.transactionlab.repository;

import com.jono.transactionlab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
