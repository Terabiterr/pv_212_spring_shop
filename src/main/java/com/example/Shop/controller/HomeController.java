package com.example.Shop.controller;
import com.example.Shop.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index() {
        return "index";
    }
    @GetMapping("/read")
    public String read(Model model) {
        return "crud/read";
    }
}