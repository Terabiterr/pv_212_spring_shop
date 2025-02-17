package com.example.Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class OrderDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @OneToMany
    private List<OrderItem> ordersItems;
    @PositiveOrZero
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderItem> getOrdersItems() {
        return ordersItems;
    }

    public void setOrdersItems(List<OrderItem> ordersItems) {
        this.ordersItems = ordersItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
