package com.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.RequestDto.AddToCartRequest;
import com.ecommerce.RequestDto.UpdateCartRequest;
import com.ecommerce.ResponseDto.ApiResponse;
import com.ecommerce.ResponseDto.CartResponse;
import com.ecommerce.Service.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartResponse response = cartService.addToCart(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product added to cart successfully",response));
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUserId(@PathVariable Long userId) {
        CartResponse response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>("Cart fetched successfully",response));
    }

   
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(@Valid @RequestBody UpdateCartRequest request) {
        CartResponse response = cartService.updateCartItem(request);
        return ResponseEntity.ok(new ApiResponse<>("Cart item updated successfully",response));
    }

    
    @PatchMapping("/{cartItemId}/quantity")
    public ResponseEntity<ApiResponse<CartResponse>> updateQuantity(@PathVariable Long cartItemId,@RequestParam Integer quantity) {
        UpdateCartRequest request = new UpdateCartRequest();
        request.setCartItemId(cartItemId);
        request.setQuantity(quantity);
        CartResponse response = cartService.updateCartItem(request);
        return ResponseEntity.ok(new ApiResponse<>("Cart quantity updated successfully",response));
    }

   
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<String>> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok(new ApiResponse<>("Cart item removed successfully","Deleted"));
    }
}