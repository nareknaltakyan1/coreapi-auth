package com.nnaltakyan.core.auth.domain.verification.service;

import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.events.EventPublisher;
import com.nnaltakyan.core.auth.domain.verification.events.kafka.SendEmailKafkaProducer;
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
    private final EventPublisher eventPublisher;
    private SendEmailKafkaProducer kafkaProducer;
    private static final String DEFAULT_CHARSET = "0123456789";
    private static final int DEFAULT_LENGTH = 6;

    public void createOTPAndSaveInDB(User user){
        final Long otp = this.generateOTP();
        this.saveOTP(user.getId(), otp);
        System.out.println(otp);
        // add event flow
        eventPublisher.publishEvent("Sending verification email to user:" + user.getFirstName());
        kafkaProducer.sendMessage("verification", "Sending verification email to user:" );
    }

    public Long generateOTP() {
        final SecureRandom random = new SecureRandom();
        Long otp = 100000 + random.nextLong(900000);
        log.info("Generated otp: {}", otp);
        return otp;
    }

    public void saveOTP(Long userId, Long otp){
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
        Verification verification = Verification.builder()
                .userid(userId)
                .otp(otp)
                .created(currentTimestamp)
                .build();
        verificationRepository.save(verification);
        System.out.println("userId: " + userId);
        System.out.println("OTP: " + otp);
        log.info("Otp saved to db");
    }

    public VerificationResponse verify(VerificationRequest request) throws Exception {
        // todo do we need to throw an exception
        log.info("Verifying the user with email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        // check for not null
        Verification verification = verificationRepository.findByUserid(user.getId())
               .orElseThrow(()->new Exception("Otp not found"));
        if (Objects.equals(verification.getOtp(), Long.valueOf(request.getOtp()))){
            verification.setVerified(true);
            log.info("OTP match!");
            verificationRepository.save(
                    new Verification(
                            verification.getId(),
                            verification.getUserid(),
                            verification.getOtp(),
                            verification.getCreated(),
                            verification.getVerified()));
        }
        return VerificationResponse.builder()
                .userId(user.getId())
                .verified(verification.getVerified())
                .build();
    }
}
