package com.ecommerce.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ResponseDto.ApiResponse;
import com.ecommerce.ResponseDto.OrderResponse;
import com.ecommerce.Service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<OrderResponse> response =
                orderService.getAllOrders(page, size);

        return ResponseEntity.ok(
                new ApiResponse<>("All orders fetched successfully", response));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> response = orderService.getAllOrdersByUserId(userId);

        return ResponseEntity.ok(new ApiResponse<>("Orders fetched successfully", response));
    }
   
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long orderId) {
        OrderResponse response =orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse<>("Order fetched successfully",response));
    }
}