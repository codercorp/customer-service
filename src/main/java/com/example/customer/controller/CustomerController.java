package com.example.customer.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.example.customer.repo.CustomerRepository;
import com.example.customer.model.Customer;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository repo;

    @GetMapping("/customers")
    public List<Customer> all() {
        return repo.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer get(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping("/customer")
    public Customer create(@RequestBody Customer obj) {
        return repo.save(obj);
    }

    @PutMapping("/customer/{id}")
    public Customer update(@PathVariable String id, @RequestBody Customer obj) {
        obj.setId(id);
        return repo.save(obj);
    }

    @DeleteMapping("/customer/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }
}
