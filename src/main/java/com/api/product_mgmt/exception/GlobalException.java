package com.api.product_mgmt.exception;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalException extends RuntimeException {

    private ResponseEntity<ProblemDetail> problemDetailResponseEntity(Map<?, ?> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("error", errors);
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ProblemDetail> problemDetailResponseEntityCustom(String error,
            HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setDetail(error);
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return problemDetailResponseEntity(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ProblemDetail> handlerMethodValidationException(
            HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();
        // Loop through each invalid parameter validation result
        e.getParameterValidationResults().forEach(parameterError -> {
            String paramName = parameterError.getMethodParameter().getParameterName();

            // Loop through each validation error message for this parameter
            for (var messageError : parameterError.getResolvableErrors()) {
                errors.put(paramName, messageError.getDefaultMessage()); // Store error message
            }
        });

        // Create structured ProblemDetail response
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Method Parameter Validation Failed");
        problemDetail.setProperties(Map.of("timestamp", LocalDateTime.now(), "errors", errors));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return problemDetailResponseEntityCustom(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return problemDetailResponseEntityCustom(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleAllUnhandledExceptions(Exception e) {
        logger.error("Unhandled exception occurred", e);
        StackTraceElement[] stack = e.getStackTrace();
        String errorMsg = "";
        if (stack.length > 0) {
            StackTraceElement origin = stack[0];
            errorMsg = e.getClass().getSimpleName() + " at " + origin.getClassName() + "."
                    + origin.getMethodName() + "(" + origin.getFileName() + ":"
                    + origin.getLineNumber() + ")";
        } else {
            errorMsg = e.getClass().getSimpleName();
        }
        return problemDetailResponseEntityCustom(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
