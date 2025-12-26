package com.example.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "customers")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;

}
