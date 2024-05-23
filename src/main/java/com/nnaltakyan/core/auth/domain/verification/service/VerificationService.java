package com.nnaltakyan.core.auth.domain.verification.service;

import com.nnaltakyan.core.auth.domain.user.enums.UserStatus;
import com.nnaltakyan.core.auth.domain.user.exceptions.UserNotFoundException;
import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.exceptions.VerificationException;
import com.nnaltakyan.core.auth.domain.verification.model.Verification;
import com.nnaltakyan.core.auth.domain.verification.repository.VerificationRepository;
import com.nnaltakyan.core.auth.domain.verification.utils.VerficationUtils;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final static String VERIFICATION_NOT_FOUND = "Verification record not found.";
    private final static String USER_NOT_FOUND = "User record not found.";
    public void createOTPAndSaveInDB(User user){
        final Long otp = VerficationUtils.generateOTP();
        this.saveOTP(user.getId(), otp);
    }

    @Transactional
    public void saveOTP(Long userId, Long otp){
        final Verification verification = Verification.builder()
                .userid(userId)
                .verificationCode(otp)
                .created(Timestamp.valueOf(now()))
                .build();
        verificationRepository.save(verification);
        log.info("Otp for user with id {} saved in the DB.", userId);
    }

    @Transactional
    public VerificationResponse verify(VerificationRequest request) throws VerificationException {
        log.info("Verifying the user with email: {}", request.getEmail());
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .verified(false)
                .build();
            Verification verification = verificationRepository.findLastRecordByEmail(request.getEmail())
                    .orElseThrow(() -> new VerificationException(VERIFICATION_NOT_FOUND));
            User user = userRepository.findById(verification.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
            if (Objects.equals(verification.getVerificationCode(), request.getVerificationCode())) {
                log.info("OTP match!");
                verification.setVerified(true);
                verificationRepository.save(verification);
                user.setStatus(UserStatus.VERIFIED);
                userRepository.save(user);
                verificationResponse.setVerified(true);
            }
            return verificationResponse;
    }
}
