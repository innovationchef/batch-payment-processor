package com.innovationchef.service;

import com.innovationchef.constant.PaymentStatus;
import com.innovationchef.exception.ApiException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class PaymentApiCall {

    private static final int MIN = 1;
    private static final int MAX = 50;

    @SneakyThrows
    @Retryable(value = ApiException.class)
    public PaymentStatus pay() {
        Random r = new Random();
        TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX - MIN + 1) + MIN);
        if (r.nextInt(50) < 25) return PaymentStatus.ACTC;
        throw new ApiException();
    }

    @Recover
    public PaymentStatus recoverPay(ApiException ex) {
        return PaymentStatus.RJCT;
    }
}
