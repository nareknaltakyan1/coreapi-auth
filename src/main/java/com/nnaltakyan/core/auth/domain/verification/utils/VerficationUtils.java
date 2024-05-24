package com.nnaltakyan.core.auth.domain.verification.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Slf4j
@Component
public class VerficationUtils {
    public static Long generateVerificationCode() {
        final SecureRandom random = new SecureRandom();
        Long verificationCode = 100000 + random.nextLong(900000);
        log.info("Generated verification code: {}", verificationCode);
        return verificationCode;
    }
}
