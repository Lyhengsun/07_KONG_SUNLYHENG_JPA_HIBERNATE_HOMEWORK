package com.api.product_mgmt.utils;

import com.api.product_mgmt.exception.NotFoundException;

public class ServiceValidationUtils {
    public static void notFoundValidation(Boolean condition, String message) {
        if (condition) {
            throw new NotFoundException(message);
        }
    }
}
