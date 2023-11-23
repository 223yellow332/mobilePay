package com.calmdown.mobilePay.domain.dordy.application;

import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceUnitTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void cert_test(){

        //given

        //when

        //then
    }

}
