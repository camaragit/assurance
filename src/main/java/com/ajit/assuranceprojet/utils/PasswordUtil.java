package com.ajit.assuranceprojet.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String getPassWordHash(String password) {
        return encoder.encode(password);
    }
}
