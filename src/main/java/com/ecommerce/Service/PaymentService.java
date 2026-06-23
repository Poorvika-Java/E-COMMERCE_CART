package com.ecommerce.Service;

import java.math.BigDecimal;

public interface PaymentService {

    boolean processPayment(BigDecimal amount);
}