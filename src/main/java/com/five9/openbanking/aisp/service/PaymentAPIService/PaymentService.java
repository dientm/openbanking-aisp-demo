package com.five9.openbanking.aisp.service.PaymentAPIService;

public interface PaymentService {
    String setupPaymentRequest();

    String submitPayment(String code, String state);
}
