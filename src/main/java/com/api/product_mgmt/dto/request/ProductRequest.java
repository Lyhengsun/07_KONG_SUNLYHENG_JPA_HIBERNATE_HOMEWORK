package com.api.product_mgmt.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "name is required")
    @NotEmpty(message = "name can't be blank")
    private String name;

    @NotNull(message = "price is required")
    @Positive(message = "price can't be a negative number or zero")
    @Digits(integer = 6, fraction = 2, message = "price must be a valid 6 integer number with up to 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "quantity is required")
    @PositiveOrZero(message = "price can't be a negative number")
    @Digits(integer = 5, fraction = 0, message = "quantity can't be higher than 5 digits")
    private Integer quantity;
}
