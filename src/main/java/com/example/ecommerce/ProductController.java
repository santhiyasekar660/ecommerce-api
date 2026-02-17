package com.example.ecommerce;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") 
public class ProductController {

    // In-memory storage (ArrayList)
    private final List<Product> items = new CopyOnWriteArrayList<>();
    private long idCounter = 1;

    // 1. Add a new item (POST)
    @PostMapping
    public ResponseEntity<Product> addItem(@Valid @RequestBody Product product) {
        product.setId(idCounter++);
        items.add(product);
        return ResponseEntity.status(201).body(product);
    }

    // 2. Get a single item by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getItemById(@PathVariable Long id) {
        return items.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get all items
    @GetMapping
    public List<Product> getAllItems() {
        return items;
    }
}
