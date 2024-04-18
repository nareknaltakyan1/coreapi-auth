package com.nnaltakyan.core.auth.rest.authentication.endpoint;

import com.nnaltakyan.core.auth.domain.service.auth.AuthenticationService;
import com.nnaltakyan.core.auth.rest.authentication.api.AuthApi;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticateRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticationResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi
{

	private final AuthenticationService authenticationService;

	@Override
	public ResponseEntity<AuthenticationResponse> register(final RegisterRequest registerRequest)
	{
		return ResponseEntity.ok(authenticationService.register(registerRequest));
	}

	@Override
	public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest authenticateRequest)
	{
		return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
	}
}
