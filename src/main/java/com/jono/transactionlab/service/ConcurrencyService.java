package com.jono.transactionlab.service;

import com.jono.transactionlab.entity.Product;
import com.jono.transactionlab.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
@Service
public class ConcurrencyService {

    private final ProductRepository productRepository;

    public ConcurrencyService(
            ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Transactional
    public void orderPizza(Long productId) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow();

        System.out.println(
                "Thread: "
                        + Thread.currentThread().getName()
        );

        System.out.println(
                "Stock read = "
                        + product.getStock()
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        product.setStock(
                product.getStock() - 1
        );

        productRepository.save(product);

        System.out.println(
                "Stock updated = "
                        + product.getStock()
        );
    }
}