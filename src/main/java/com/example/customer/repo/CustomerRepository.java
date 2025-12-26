package com.example.customer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.customer.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {}
