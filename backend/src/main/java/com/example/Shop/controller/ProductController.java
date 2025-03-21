package com.example.Shop.controller;

import com.example.Shop.Service.ProductService;
import com.example.Shop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String read(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "crud/read";
    }
    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("product", new Product());
        return "crud/save";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {

        productService.addProduct(product);
        return "redirect:/products";
    }
}
