package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.ecommerce.Entity.*;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.Exception.CartEmptyException;
import com.ecommerce.Exception.InsufficientStockException;
import com.ecommerce.Repository.*;
import com.ecommerce.RequestDto.CheckoutRequest;
import com.ecommerce.ResponseDto.CheckoutResponse;
import com.ecommerce.Service.CouponService;
import com.ecommerce.Service.PaymentService;
import com.ecommerce.ServiceImpl.CheckoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CouponService couponService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private User user;
    private Product product;
    private Cart cart;
    private CartItem cartItem;

    @BeforeEach
    void setup() {

        user = User.builder()
                .id(1L)
                .name("Poorvika")
                .email("poorvika@gmail.com")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(new BigDecimal("50000"))
                .stockQuantity(10)
                .build();

        cartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(1)
                .build();

        cart = Cart.builder()
                .id(1L)
                .user(user)
                .cartItems(List.of(cartItem))
                .build();
    }

    @Test
    void shouldCheckoutSuccessfully() {

        CheckoutRequest request = new CheckoutRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(cartRepository.findByUserId(1L))
                .thenReturn(Optional.of(cart));

        when(couponService.calculateDiscount(any(),any()))
                .thenReturn(BigDecimal.ZERO);

        when(paymentService.processPayment(any()))
                .thenReturn(true);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        CheckoutResponse response =checkoutService.checkout(request);

        assertEquals(OrderStatus.SUCCESS,response.getStatus());
    }

    @Test
    void shouldThrowCartEmptyException() {
        cart.setCartItems(List.of());

        CheckoutRequest request = new CheckoutRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(cartRepository.findByUserId(1L))
                .thenReturn(Optional.of(cart));

        assertThrows(CartEmptyException.class,
                () -> checkoutService.checkout(request));
    }

    @Test
    void shouldThrowInsufficientStockException() {
        product.setStockQuantity(0);

        CheckoutRequest request = new CheckoutRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(cartRepository.findByUserId(1L))
                .thenReturn(Optional.of(cart));

        assertThrows(InsufficientStockException.class,
                () -> checkoutService.checkout(request));
    }

    @Test
    void shouldHandlePaymentFailure() {
        CheckoutRequest request = new CheckoutRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(cartRepository.findByUserId(1L))
                .thenReturn(Optional.of(cart));

        when(couponService.calculateDiscount(any(),any()))
                .thenReturn(BigDecimal.ZERO);

        when(paymentService.processPayment(any()))
                .thenReturn(false);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        CheckoutResponse response =checkoutService.checkout(request);

        assertEquals(OrderStatus.FAILED,response.getStatus());
    }
}