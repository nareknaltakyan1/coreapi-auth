//package com.nnaltakyan.core.auth.domain;
//
//import com.nnaltakyan.core.auth.domain.user.model.User;
//import com.nnaltakyan.core.auth.domain.user.model.Verification;
//import com.nnaltakyan.core.auth.domain.user.service.VerificationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.security.SecureRandom;
//
//@Service
//@RequiredArgsConstructor
//public class VerificationService {
//
//    private final VerificationRepository verificationRepository;
//    private static final String DEFAULT_CHARSET = "0123456789";
//    private static final int DEFAULT_LENGTH = 6;
//
//    public static String generateOTP() {
//        return generateOTP(DEFAULT_CHARSET, DEFAULT_LENGTH);
//    }
//
//    public void verify(){
//        String otp = VerificationService.generateOTP();
//
//    }
//
//    public static String generateOTP(int length) {
//        return generateOTP(DEFAULT_CHARSET, length);
//    }
//
//    public static String generateOTP(String charset, int length) {
//        if (charset == null || charset.isEmpty() || length <= 0) {
//            throw new IllegalArgumentException("Invalid parameters");
//        }
//        SecureRandom random = new SecureRandom();
//        StringBuilder otp = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            otp.append(charset.charAt(random.nextInt(charset.length())));
//        }
//        return otp.toString();
//    }
//
//    public void saveOtp(User user){
//        // check
//        Verification verification = Verification.builder()
//                .email(user.getEmail())
//                .verified(false)
//                .build();
//        verificationRepository.save(verification);
//    }
//
//    //send a message from kafka
//
//}
