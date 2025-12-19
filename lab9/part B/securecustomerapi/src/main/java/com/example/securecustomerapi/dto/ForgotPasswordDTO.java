package com.example.securecustomerapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordDTO {
    @NotBlank
    @Email
    private String email;

    // Constructors
    public ForgotPasswordDTO() {
    }

    public ForgotPasswordDTO(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}