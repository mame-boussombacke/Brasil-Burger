package com.brasilburger.utils;
public class InputValidator {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static boolean isPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{8,15}");
    }
}
