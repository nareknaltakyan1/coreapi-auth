package com.nnaltakyan.core.auth.domain.auth.service;

import com.nnaltakyan.core.auth.domain.user.enums.UserStatus;
import com.nnaltakyan.core.auth.domain.user.model.Role;
import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.jwt.service.JwtService;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.events.EventPublisher;
import com.nnaltakyan.core.auth.domain.verification.events.SendVerificationEmailEvent;
import com.nnaltakyan.core.auth.domain.verification.service.VerificationService;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticateRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticationResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final VerificationService verificationService;
	private final EventPublisher eventPublisher;

	public RegisterResponse register(final RegisterRequest registerRequest)
	{
		var user = User.builder().firstName(registerRequest.getFirstName()).lastName(registerRequest.getLastName()).email(registerRequest.getEmail())
			.password(passwordEncoder.encode(registerRequest.getPassword())).role(Role.USER).created(registerRequest.getCreated())
			.updated(registerRequest.getUpdated()).build();
		userRepository.save(user);
		verificationService.createOTPAndSaveInDB(user);
		if (Objects.nonNull(user.getId())){
			SendVerificationEmailEvent sendVerificationEmailEvent = new SendVerificationEmailEvent(this, user.getId());
			eventPublisher.publishEvent(sendVerificationEmailEvent);
			if (Objects.nonNull(user.getStatus())){
				return RegisterResponse.builder()
						.userId(user.getId())
						.status(user.getStatus().getStatus())
						.email(user.getEmail()).build();
			}
		}
		return RegisterResponse.builder()
			.userId(-1L)
			.status(null)
			.email(user.getEmail()).build();

	}

	public AuthenticationResponse authenticate(final AuthenticateRequest authenticateRequest)
	{
		authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
		var user = userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		var jwt = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwt).build();
	}
}
