package com.api.product_mgmt.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseResponse {
    public <T> ResponseEntity<ApiResponse<T>> responseEntity(String message, HttpStatus httpStatus, T payload) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder().message(message)
                .status(httpStatus).code(httpStatus.value()).payload(payload).build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
