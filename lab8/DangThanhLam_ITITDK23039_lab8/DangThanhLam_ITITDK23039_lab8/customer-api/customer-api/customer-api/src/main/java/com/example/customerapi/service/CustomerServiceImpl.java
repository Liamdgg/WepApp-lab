package com.example.customerapi.service;

import com.example.customerapi.dto.CustomerRequestDTO;
import com.example.customerapi.dto.CustomerResponseDTO;
import com.example.customerapi.entity.Customer;
import com.example.customerapi.exception.DuplicateResourceException;
import com.example.customerapi.exception.ResourceNotFoundException;
import com.example.customerapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String sortDir) {
        org.springframework.data.domain.Pageable pageable;
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            org.springframework.data.domain.Sort sort = "desc".equalsIgnoreCase(sortDir)
                    ? org.springframework.data.domain.Sort.by(sortBy).descending()
                    : org.springframework.data.domain.Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<com.example.customerapi.entity.Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.map(this::convertToResponseDTO);
    }
    
    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return convertToResponseDTO(customer);
    }
    
    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        // Check for duplicates
        if (customerRepository.existsByCustomerCode(requestDTO.getCustomerCode())) {
            throw new DuplicateResourceException("Customer code already exists: " + requestDTO.getCustomerCode());
        }
        
        if (customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        // Convert DTO to Entity
        Customer customer = convertToEntity(requestDTO);
        
        // Save to database
        Customer savedCustomer = customerRepository.save(customer);
        
        // Convert Entity to Response DTO
        return convertToResponseDTO(savedCustomer);
    }
    
    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        // Check if email is being changed to an existing one
        if (!existingCustomer.getEmail().equals(requestDTO.getEmail()) 
            && customerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        // Update fields
        existingCustomer.setFullName(requestDTO.getFullName());
        existingCustomer.setEmail(requestDTO.getEmail());
        existingCustomer.setPhone(requestDTO.getPhone());
        existingCustomer.setAddress(requestDTO.getAddress());
        
        // Don't update customerCode (immutable)
        
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToResponseDTO(updatedCustomer);
    }
    
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
    
    @Override
    public List<CustomerResponseDTO> searchCustomers(String keyword) {
        return customerRepository.searchCustomers(keyword)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerResponseDTO> getCustomersByStatus(String status) {
        List<Customer> customers;
        if (status == null || status.trim().isEmpty()) {
            customers = customerRepository.findAll();
        } else {
            try {
                com.example.customerapi.entity.CustomerStatus enumStatus = com.example.customerapi.entity.CustomerStatus.valueOf(status.trim().toUpperCase());
                customers = customerRepository.findByStatus(enumStatus);
            } catch (IllegalArgumentException ex) {
                // invalid status value, return empty list
                return List.of();
            }
        }
        return customers.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponseDTO> advancedSearch(String name, String email, String status) {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .filter(c -> {
                    boolean ok = true;
                    if (name != null && !name.trim().isEmpty()) {
                        String n = name.trim().toLowerCase();
                        ok = ok && c.getFullName() != null && c.getFullName().toLowerCase().contains(n);
                    }
                    if (email != null && !email.trim().isEmpty()) {
                        String e = email.trim().toLowerCase();
                        ok = ok && c.getEmail() != null && c.getEmail().toLowerCase().contains(e);
                    }
                    if (status != null && !status.trim().isEmpty()) {
                        try {
                            com.example.customerapi.entity.CustomerStatus enumStatus = com.example.customerapi.entity.CustomerStatus.valueOf(status.trim().toUpperCase());
                            ok = ok && c.getStatus() == enumStatus;
                        } catch (IllegalArgumentException ex) {
                            // invalid status filter -> no results for this item
                            return false;
                        }
                    }
                    return ok;
                })
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO partialUpdateCustomer(Long id, com.example.customerapi.dto.CustomerUpdateDTO updateDTO) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (updateDTO.getFullName() != null) {
            existing.setFullName(updateDTO.getFullName());
        }

        if (updateDTO.getEmail() != null) {
            String newEmail = updateDTO.getEmail();
            if (!newEmail.equals(existing.getEmail()) && customerRepository.existsByEmail(newEmail)) {
                throw new com.example.customerapi.exception.DuplicateResourceException("Email already exists: " + newEmail);
            }
            existing.setEmail(newEmail);
        }

        if (updateDTO.getPhone() != null) {
            existing.setPhone(updateDTO.getPhone());
        }

        if (updateDTO.getAddress() != null) {
            existing.setAddress(updateDTO.getAddress());
        }

        Customer saved = customerRepository.save(existing);
        return convertToResponseDTO(saved);
    }
    
    // Helper Methods for DTO Conversion
    
    private CustomerResponseDTO convertToResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setCustomerCode(customer.getCustomerCode());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setStatus(customer.getStatus().toString());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
    
    private Customer convertToEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerCode(dto.getCustomerCode());
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}
