package com.example.Shop.controller;

import com.example.Shop.model.OrderDemo;
import com.example.Shop.model.OrderItem;
import com.example.Shop.model.Product;
import com.example.Shop.repository.OrderDemoRepository;
import com.example.Shop.repository.OrderItemRepository;
import com.example.Shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class RestOrderDemoController {
    private final OrderDemoRepository orderDemoRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RestOrderDemoController(OrderDemoRepository orderDemoRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderDemoRepository = orderDemoRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        List<OrderDemo> orderDemos = orderDemoRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("count", orderDemos.size());
        response.put("orders", orderDemos);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable long id) {
        Optional<OrderDemo> orderDemo = orderDemoRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (orderDemo.isPresent()) {
            response.put("order", orderDemo.get());
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Order Not Found");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderDemo orderDemo) {
        Map<String, Object> response = new HashMap<>();
        if(orderDemo.getOrdersItems() == null || orderDemo.getOrdersItems().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Order must contain at least one item");
            return ResponseEntity.badRequest().body(response);
        }
        BigDecimal totalPrice = orderDemo.getOrdersItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDemo.setTotal(totalPrice);
        OrderDemo orderDemoSaved = orderDemoRepository.save(orderDemo);
        orderDemo.getOrdersItems().stream()
                .forEach(item -> {
                    item.setOrder(orderDemoSaved);
                    orderItemRepository.save(item);
                });
        response.put("status", "success");
        response.put("order", orderDemoSaved);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addItemToOrder(@PathVariable long id, @RequestBody OrderItem newItem) {
        Map<String, Object> response = new HashMap<>();
        Optional<OrderDemo> orderDemo = orderDemoRepository.findById(id);
        if (orderDemo.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Order Not Found");
            return ResponseEntity.badRequest().body(response);
        }
        OrderDemo orderDemoSaved = orderDemo.get();
        if(newItem.getQuantity() <= 0 || newItem.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            response.put("status", "error");
            response.put("message", "Quantity must be greater than zero");
            return ResponseEntity.badRequest().body(response);
        }
        orderDemoSaved.getOrdersItems().add(newItem);
        Product product = productRepository.findById(newItem.getProduct().getId()).get();
        newItem.setPrice(product.getPrice()
                .multiply(BigDecimal.valueOf(newItem.getQuantity())));
        orderDemoSaved.setTotal(orderDemoSaved.getTotal().add(newItem.getPrice()));
        orderItemRepository.save(newItem);
        orderDemoRepository.save(orderDemoSaved);
        response.put("status", "success");
        response.put("order", orderDemoSaved);
        return ResponseEntity.ok(response);
    }
    //DZ - 03.10.2025
    //Реалізувати звичайний контролер OrderDemoController
    //@PutMapping("/{id}")
    //updateOrderDemo
    //
}
