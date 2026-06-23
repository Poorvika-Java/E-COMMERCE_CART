package com.ecommerce.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.ecommerce.ResponseDto.OrderResponse;

public interface OrderService {

    Page<OrderResponse> getOrdersByUserId(Long userId,int page,int size);

    OrderResponse getOrderById(Long orderId);
    
    List<OrderResponse> getAllOrdersByUserId(Long userId);
    
    Page<OrderResponse> getAllOrders(int page, int size);
}
