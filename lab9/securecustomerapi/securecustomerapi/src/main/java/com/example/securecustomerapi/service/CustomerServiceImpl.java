package com.example.securecustomerapi.service;

import com.example.securecustomerapi.dto.CustomerRequestDTO;
import com.example.securecustomerapi.dto.CustomerResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final List<CustomerResponseDTO> storage = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return new ArrayList<>(storage);
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        return storage.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        CustomerResponseDTO dto = new CustomerResponseDTO(
                idCounter.getAndIncrement(),
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                LocalDateTime.now()
        );
        storage.add(dto);
        return dto;
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request) {
        CustomerResponseDTO existing = getCustomerById(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setAddress(request.getAddress());
        return existing;
    }

    @Override
    public void deleteCustomer(Long id) {
        storage.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public List<CustomerResponseDTO> searchCustomers(String keyword) {
        String k = keyword == null ? "" : keyword.toLowerCase();
        return storage.stream()
                .filter(c -> c.getName().toLowerCase().contains(k) || (c.getEmail() != null && c.getEmail().toLowerCase().contains(k)))
                .collect(Collectors.toList());
    }
}
