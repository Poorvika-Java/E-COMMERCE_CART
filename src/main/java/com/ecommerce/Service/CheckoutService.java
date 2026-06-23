package com.ecommerce.Service;

import com.ecommerce.RequestDto.CheckoutRequest;
import com.ecommerce.ResponseDto.CheckoutResponse;

public interface CheckoutService {

    CheckoutResponse checkout(CheckoutRequest request);
}