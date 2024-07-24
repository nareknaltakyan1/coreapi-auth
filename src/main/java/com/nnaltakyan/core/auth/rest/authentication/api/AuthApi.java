package com.nnaltakyan.core.auth.rest.authentication.api;

import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticateRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticationResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthApi
{

	@PostMapping("/register")
	ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest);

	@PostMapping("/authenticate")
	ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest);

	// we need to throw both kafka and spring events
	@PostMapping("/verify")
	ResponseEntity<VerificationResponse> verify(@RequestBody VerificationRequest verificationRequest) throws Exception;
}
