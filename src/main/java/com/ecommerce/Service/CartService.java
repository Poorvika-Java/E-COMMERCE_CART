package com.ecommerce.Service;

import com.ecommerce.RequestDto.AddToCartRequest;
import com.ecommerce.RequestDto.UpdateCartRequest;
import com.ecommerce.ResponseDto.CartResponse;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse updateCartItem(UpdateCartRequest request);

    void removeCartItem(Long cartItemId);

    CartResponse getCartByUserId(Long userId);
}