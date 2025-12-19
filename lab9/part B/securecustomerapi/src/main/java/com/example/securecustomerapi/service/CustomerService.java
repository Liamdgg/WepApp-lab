package com.example.securecustomerapi.service;

import com.example.securecustomerapi.dto.CustomerRequestDTO;
import com.example.securecustomerapi.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO getCustomerById(Long id);
    CustomerResponseDTO createCustomer(CustomerRequestDTO request);
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request);
    void deleteCustomer(Long id);
    List<CustomerResponseDTO> searchCustomers(String keyword);
}
