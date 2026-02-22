package com.example.spring_mvc_lab.controller;

import com.example.spring_mvc_lab.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsController {

    private final List<Product> products = new ArrayList<>();

    public StatisticsController() {
        products.add(new Product(1L, "Laptop ASUS", "Elektronik", 12500000, 5));
        products.add(new Product(2L, "Mouse Logitech", "Elektronik", 350000, 50));
        products.add(new Product(3L, "Buku Java", "Buku", 150000, 8));
        products.add(new Product(4L, "Keyboard", "Elektronik", 500000, 12));
    }

    public List<Product> findAll() {
        return products;
    }


    public Map<String, Long> countByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.counting()
                ));
    }

    public Product getMostExpensive() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public Product getCheapest() {
        return products.stream()
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public double getAveragePrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);
    }

    public long countLowStock() {
        return products.stream()
                .filter(p -> p.getStock() < 10)
                .count();
    }
}