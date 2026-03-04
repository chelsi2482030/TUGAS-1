package com.example.spring_mvc_lab.controller;

import com.example.spring_mvc_lab.model.Product;
import com.example.spring_mvc_lab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {

    private final ProductService productService;

    public StatisticsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {

        List<Product> products = productService.findAll(); // ✅ BENAR

        model.addAttribute("title", "Statistik Produk");
        model.addAttribute("totalProducts", products.size());

        Map<String, Long> totalPerCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.counting()));

        model.addAttribute("totalPerCategory", totalPerCategory);

        Product mostExpensive = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);

        model.addAttribute("mostExpensive", mostExpensive);

        Product cheapest = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElse(null);

        model.addAttribute("cheapest", cheapest);

        double avg = products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);

        model.addAttribute("averagePrice", avg);

        long lowStockCount = products.stream()
                .filter(p -> p.getStock() < 20)
                .count();

        model.addAttribute("lowStockCount", lowStockCount);

        return "statistics";
    }
}