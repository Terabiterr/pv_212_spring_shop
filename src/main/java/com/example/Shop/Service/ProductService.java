package com.example.Shop.Service;
import com.example.Shop.model.Product;
import com.example.Shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductRepository {
    private List<Product> products = new ArrayList<>();
    public ProductService() {
        this.products.add(new Product(1, 4, 2500.00, "iphone 6S", "Apple"));
        this.products.add(new Product(2, 2, 252200.00, "iphone 7S", "Apple"));
        this.products.add(new Product(3, 10, 23423.00, "iphone 8S", "Apple"));
    }
    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> findById(long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }

    @Override
    public void update(long id, Product product) {
        products.set(products.indexOf(findById(id)), product);
    }
}
