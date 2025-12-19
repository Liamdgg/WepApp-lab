package com.example.securecustomerapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java PasswordHashGenerator <password>");
            System.exit(2);
        }
        String plain = args[0];
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        System.out.println(enc.encode(plain));
    }
}
