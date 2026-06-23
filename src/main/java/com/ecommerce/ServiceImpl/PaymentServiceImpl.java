package com.ecommerce.ServiceImpl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.ecommerce.Service.PaymentService;


@Service
public class PaymentServiceImpl implements PaymentService {

	    @Override
	    public boolean processPayment(BigDecimal amount) {

	        System.out.println("PAYMENT AMOUNT = " + amount);

	        return amount.compareTo(new BigDecimal("100000")) <= 0;
	    }
	}