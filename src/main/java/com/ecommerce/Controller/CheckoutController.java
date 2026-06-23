package com.ecommerce.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.RequestDto.CheckoutRequest;
import com.ecommerce.ResponseDto.ApiResponse;
import com.ecommerce.ResponseDto.CheckoutResponse;
import com.ecommerce.Service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(@Valid @RequestBody CheckoutRequest request) {
        CheckoutResponse response =checkoutService.checkout(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Checkout completed",response));
    }
}