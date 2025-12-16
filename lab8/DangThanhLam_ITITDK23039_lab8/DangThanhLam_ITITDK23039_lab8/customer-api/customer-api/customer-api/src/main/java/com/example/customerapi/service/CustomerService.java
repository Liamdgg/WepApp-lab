package com.example.customerapi.service;

import com.example.customerapi.dto.CustomerRequestDTO;
import com.example.customerapi.dto.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    
    Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String sortDir);
    
    CustomerResponseDTO getCustomerById(Long id);
    
    CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO);
    
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO);
    
    void deleteCustomer(Long id);
    
    List<CustomerResponseDTO> searchCustomers(String keyword);
    
    List<CustomerResponseDTO> getCustomersByStatus(String status);
    
    List<CustomerResponseDTO> advancedSearch(String name, String email, String status);

    CustomerResponseDTO partialUpdateCustomer(Long id, com.example.customerapi.dto.CustomerUpdateDTO updateDTO);
}
