package com.example.securecustomerapi.controller;

import com.example.securecustomerapi.dto.UserResponseDTO;
import com.example.securecustomerapi.entity.User;
import com.example.securecustomerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> listUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(u -> new UserResponseDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getFullName(),
                u.getRole().name(),
                u.getIsActive(),
                u.getCreatedAt()
        )).collect(Collectors.toList());
    }
}
