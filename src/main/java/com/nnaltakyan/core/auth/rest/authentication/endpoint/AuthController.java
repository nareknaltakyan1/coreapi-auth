package com.nnaltakyan.core.auth.rest.authentication.endpoint;

import com.nnaltakyan.core.auth.domain.auth.service.AuthenticationService;
import com.nnaltakyan.core.auth.domain.verification.service.VerificationService;
import com.nnaltakyan.core.auth.rest.authentication.api.AuthApi;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticateRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticationResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.VerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi
{

	private final AuthenticationService authenticationService;
	private final VerificationService verificationService;

	@Override
	public ResponseEntity<RegisterResponse> register(final RegisterRequest registerRequest)
	{
		return ResponseEntity.ok(authenticationService.register(registerRequest));
	}

	@Override
	public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest authenticateRequest)
	{
		return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
	}

	@Override
	public ResponseEntity<VerificationResponse> verify(final VerificationRequest verificationRequest) throws Exception {
		return ResponseEntity.ok(verificationService.verify(verificationRequest));
	}
}
