package com.nnaltakyan.core.auth.domain.service.auth;

import com.nnaltakyan.core.auth.domain.model.user.Role;
import com.nnaltakyan.core.auth.domain.model.user.User;
import com.nnaltakyan.core.auth.domain.service.jwt.JwtService;
import com.nnaltakyan.core.auth.domain.service.user.UserRepository;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticateRequest;
import com.nnaltakyan.core.auth.rest.authentication.dto.AuthenticationResponse;
import com.nnaltakyan.core.auth.rest.authentication.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(final RegisterRequest registerRequest)
	{
		var user = User.builder().firstName(registerRequest.getFirstName()).lastName(registerRequest.getLastName()).email(registerRequest.getEmail())
			.password(passwordEncoder.encode(registerRequest.getPassword())).role(Role.USER).build();
		userRepository.save(user);
		var jwt = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwt).build();
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
