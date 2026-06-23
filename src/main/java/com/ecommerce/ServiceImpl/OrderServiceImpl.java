package com.ecommerce.ServiceImpl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ecommerce.Entity.Order;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Repository.OrderRepository;
import com.ecommerce.ResponseDto.OrderItemResponse;
import com.ecommerce.ResponseDto.OrderResponse;
import com.ecommerce.Service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderResponse> getOrdersByUserId(Long userId,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository
                .findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id : "+ orderId));
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> items=order.getOrderItems()
                        .stream()
                        .map(item ->
                                  OrderItemResponse.builder()
                                        .productId(item.getProduct().getId())
                                        .productName(item.getProduct().getName())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .build())
                        .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .finalAmount(order.getFinalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public Page<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
}