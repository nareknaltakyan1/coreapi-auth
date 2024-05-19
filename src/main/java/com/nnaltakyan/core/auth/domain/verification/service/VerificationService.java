package com.nnaltakyan.core.auth.domain.verification.service;

import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.model.Verification;
import com.nnaltakyan.core.auth.domain.verification.repository.VerificationRepository;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    public void createOTPAndSaveInDB(User user){
        final Long otp = this.generateOTP();
        this.saveOTP(user.getId(), otp);
    }

    public Long generateOTP() {
        final SecureRandom random = new SecureRandom();
        Long otp = 100000 + random.nextLong(900000);
        log.info("Generated otp: {}", otp);
        return otp;
    }

    public void saveOTP(Long userId, Long otp){
        long currentTimeMillis = System.currentTimeMillis();
        final Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
        final Verification verification = Verification.builder()
                .userid(userId)
                .otp(otp)
                .created(currentTimestamp)
                .build();
        verificationRepository.save(verification);
        log.info("Otp for user with id {} saved in the DB.", userId);
    }

    public VerificationResponse verify(VerificationRequest request) throws Exception {
        log.info("Verifying the user with email: {}", request.getEmail());
        Verification verification = verificationRepository.findLastRecordByOtp(request.getOtp())
               .orElseThrow(()->new Exception("Otp not found"));
        // todo do we need to throw an exception
        User user = userRepository.findById(verification.getId()).orElseThrow(()->new RuntimeException("User not found!"));
        if (Objects.equals(verification.getOtp(), Long.valueOf(request.getOtp()))){
            verification.setVerified(true);
            log.info("OTP match!");
            verificationRepository.save(verification);
            user.setStatus("VERIFIED");
            userRepository.save(user);
        }
        return VerificationResponse.builder()
                .userId(verification.getUserid())
                .verified(verification.getVerified())
                .build();
    }
}
