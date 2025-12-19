package com.example.securecustomerapi.controller;

import com.example.securecustomerapi.dto.*;
import com.example.securecustomerapi.service.UserService;
import com.example.securecustomerapi.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Unauthorized");
            body.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        UserResponseDTO response = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        UserResponseDTO user = userService.getCurrentUser(username);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        // In JWT, logout is handled client-side by removing token
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully. Please remove token from client.");
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        try {
            // Get current user from SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            // Change password
            userService.changePassword(username, dto);
            
            // Return success message
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Bad Request");
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        } catch (Exception ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Internal Server Error");
            body.put("message", "An error occurred while changing password");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto) {
        try {
            String resetToken = userService.forgotPassword(dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset token generated");
            response.put("resetToken", resetToken); // In real app, send via email
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            // Don't reveal if email exists or not for security
            Map<String, String> response = new HashMap<>();
            response.put("message", "If the email exists, a reset token has been sent");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Internal Server Error");
            body.put("message", "An error occurred while processing forgot password request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        try {
            userService.resetPassword(dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Bad Request");
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        } catch (Exception ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Internal Server Error");
            body.put("message", "An error occurred while resetting password");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDTO dto) {
        try {
            LoginResponseDTO response = userService.refreshToken(dto.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Unauthorized");
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        } catch (Exception ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "Internal Server Error");
            body.put("message", "An error occurred while refreshing token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
}
