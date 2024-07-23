package com.nnaltakyan.core.auth.domain.verification.service;

import com.nnaltakyan.api.core.common.exceptions.UserNotFoundException;
import com.nnaltakyan.api.core.common.exceptions.VerificationFailedException;
import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.model.Verification;
import com.nnaltakyan.core.auth.domain.verification.repository.VerificationRepository;
import com.nnaltakyan.core.auth.domain.verification.utils.VerificationUtils;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

import static com.nnaltakyan.api.core.common.domain.UserStatus.CREATED;
import static com.nnaltakyan.api.core.common.domain.UserStatus.VERIFIED;
import static com.nnaltakyan.api.core.common.error.ErrorMessage.USER_NOT_FOUND;
import static com.nnaltakyan.api.core.common.error.ErrorMessage.VERIFICATION_FAILED;
import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService
{

	private final VerificationRepository verificationRepository;
	private final UserRepository userRepository;

	@Transactional
	public void generateAndPersistVerificationCode(User user)
	{
		final String verificationCode = VerificationUtils.generateVerificationCode();
		saveVerificationCode(user.getId(), verificationCode);
	}

	@Transactional
	public void saveVerificationCode(Long userId, String verificationCode)
	{
		final Verification verification = Verification.builder().userid(userId).code(verificationCode).created(Timestamp.valueOf(now())).build();
		verificationRepository.save(verification);
		log.info("Verification Code for user with id {} saved in the DB.", userId);
	}

	@Transactional
	public VerificationResponse verify(VerificationRequest request) throws VerificationFailedException
	{
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));
		var verificationResponse = VerificationResponse.builder().verified(false).build();
		if (!CREATED.equals(user.getStatus())){
			log.info("User id: {} is not in CREATED states", user.getId());
			// todo 23/07/2024 throw custom exception
			return verificationResponse;
		}
		log.info("Verifying the user with email: {}", request.getEmail());
		var verificationCode = verificationRepository.findVerificationCodeByUserId(user.getId())
			.orElseThrow(() -> new VerificationFailedException(VERIFICATION_FAILED.getMessage()));
		if (CREATED.equals(user.getStatus()) && Objects.equals(verificationCode, request.getVerificationCode()))
		{
			log.info("Verification Code match!");
			verificationRepository.updateVerifiedFlag(user.getId());
			userRepository.updateUserStatus(user.getId(), VERIFIED.name());
			verificationResponse.setVerified(true);
		}
		// todo 23/07/2024 add check for attempts
		return verificationResponse;
	}
}
