package com.example.Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 2, max = 255, message = "Name min: 2, max: 255")
    @Column(nullable = false, length = 255)
    private String name;
    @Size(min = 2, max = 1024, message = "Description min: 2, max: 1024")
    @Column(nullable = true, length = 1024)
    private String description;
    @Positive
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal price;
    @PositiveOrZero
    @Column(nullable = false, length = Integer.MAX_VALUE)
    private int stock;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
