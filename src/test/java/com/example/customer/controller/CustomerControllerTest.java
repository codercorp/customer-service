package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.repo.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer mockCustomer() {
        Customer c = new Customer();
        c.setId("1");
        c.setName("Digvijay");
        c.setPhone("9999999999");
        c.setEmail("digvijay@test.com");
        c.setAddress("Pune");
        return c;
    }

    @Test
    void all() throws Exception {
        Mockito.when(repo.findAll()).thenReturn(List.of(mockCustomer()));

        mockMvc.perform( org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name").value("Digvijay"));
    }

    @Test
    void get() throws Exception {
        Mockito.when(repo.findById("1"))
                .thenReturn(Optional.of(mockCustomer()));

        mockMvc.perform( org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("digvijay@test.com"));
    }

    @Test
    void create() throws Exception {
        Customer input = mockCustomer();
        input.setId(null);

        Mockito.when(repo.save(Mockito.any(Customer.class)))
                .thenReturn(mockCustomer());

        mockMvc.perform(post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Digvijay"));
    }

    @Test
    void update() throws Exception {
        Customer updated = mockCustomer();
        updated.setName("Updated Name");

        Mockito.when(repo.save(Mockito.any(Customer.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void delete() throws Exception {
        Mockito.doNothing().when(repo).deleteById("1");

        mockMvc.perform( org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/customer/1"))
                .andExpect(status().isOk());

        Mockito.verify(repo, Mockito.times(1)).deleteById("1");
    }
}
