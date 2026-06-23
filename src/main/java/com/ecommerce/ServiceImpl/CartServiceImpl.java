package com.ecommerce.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;
import com.ecommerce.Entity.Product;
import com.ecommerce.Entity.User;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Repository.CartItemRepository;
import com.ecommerce.Repository.CartRepository;
import com.ecommerce.Repository.ProductRepository;
import com.ecommerce.Repository.UserRepository;
import com.ecommerce.RequestDto.AddToCartRequest;
import com.ecommerce.RequestDto.UpdateCartRequest;
import com.ecommerce.ResponseDto.CartItemResponse;
import com.ecommerce.ResponseDto.CartResponse;
import com.ecommerce.Service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : "+ request.getUserId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : "+ request.getProductId()));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        product.getId())
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(cartItem.getQuantity()+ request.getQuantity());

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartItemRepository.save(cartItem);

        return getCartByUserId(user.getId());
    }

    @Override
    public CartResponse updateCartItem(UpdateCartRequest request) {

        CartItem cartItem = cartItemRepository
                .findById(request.getCartItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);
        return getCartByUserId(
                cartItem.getCart()
                        .getUser()
                        .getId());
    }

    @Override
    public void removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for user id : "+ userId));

        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(item -> {

                    BigDecimal totalPrice =item.getProduct()
                                    .getPrice()
                                    .multiply(BigDecimal.valueOf(item.getQuantity()));

                    return CartItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getProduct().getPrice())
                            .totalPrice(totalPrice)
                            .build();
                })
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(userId)
                .items(items)
                .totalPrice(totalAmount)
                .build();
    }

}
