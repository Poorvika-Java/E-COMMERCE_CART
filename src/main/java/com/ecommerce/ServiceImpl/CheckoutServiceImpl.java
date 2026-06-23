package com.ecommerce.ServiceImpl;

import java.math.BigDecimal;
import com.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;
import com.ecommerce.Entity.Order;
import com.ecommerce.Entity.OrderItem;
import com.ecommerce.Entity.Product;
import com.ecommerce.Entity.User;
import com.ecommerce.Exception.CartEmptyException;
import com.ecommerce.Exception.InsufficientStockException;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Repository.CartItemRepository;
import com.ecommerce.Repository.CartRepository;
import com.ecommerce.Repository.OrderRepository;
import com.ecommerce.Repository.ProductRepository;
import com.ecommerce.Repository.UserRepository;
import com.ecommerce.RequestDto.CheckoutRequest;
import com.ecommerce.ResponseDto.CheckoutResponse;
import com.ecommerce.Service.CheckoutService;
import com.ecommerce.Service.CouponService;
import com.ecommerce.Service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private final CouponService couponService;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + request.getUserId()));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        validateInventory(cart);

        BigDecimal totalAmount = calculateTotal(cart);
        BigDecimal discountAmount =couponService.calculateDiscount(request.getCouponCode(),totalAmount);
        BigDecimal finalAmount =totalAmount.subtract(discountAmount);

        System.out.println("=================================");
        System.out.println("TOTAL AMOUNT = " + totalAmount);
        System.out.println("DISCOUNT AMOUNT = " + discountAmount);
        System.out.println("FINAL AMOUNT = " + finalAmount);
        System.out.println("=================================");

        Order order = createOrder(user,cart,totalAmount,discountAmount,finalAmount);
        boolean paymentSuccess =paymentService.processPayment(finalAmount);
        System.out.println("PAYMENT SUCCESS = " + paymentSuccess);

        if (paymentSuccess) {

            reduceInventory(cart);

            order.setStatus(OrderStatus.SUCCESS);

            orderRepository.save(order);

            cartItemRepository.deleteAll(cart.getCartItems());

            return CheckoutResponse.builder()
                    .orderId(order.getId())
                    .status(OrderStatus.SUCCESS)
                    .totalAmount(totalAmount)
                    .discountAmount(discountAmount)
                    .finalAmount(finalAmount)
                    .message("Payment successful. Order created.")
                    .build();
        }

        order.setStatus(OrderStatus.FAILED);

        orderRepository.save(order);

        return CheckoutResponse.builder()
                .orderId(order.getId())
                .status(OrderStatus.FAILED)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .message("Payment failed.")
                .build();
    }
    
    private void validateInventory(Cart cart) {
        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();

            if (product.getStockQuantity()
                    < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product : "+ product.getName());
            }
        }
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private Order createOrder(User user,Cart cart,BigDecimal totalAmount,BigDecimal discountAmount,BigDecimal finalAmount) {
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem =OrderItem.builder()
                            .order(order)
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getProduct().getPrice())
                            .build();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    private void reduceInventory(Cart cart) {
        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(
                    product.getStockQuantity()
                            - item.getQuantity());

            productRepository.save(product);
        }
    }

	
}