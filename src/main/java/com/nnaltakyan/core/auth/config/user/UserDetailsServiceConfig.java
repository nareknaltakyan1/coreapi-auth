package com.nnaltakyan.core.auth.config.user;

import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig
{

	private final UserRepository userRepository;

	@Bean
	public UserDetailsService userDetailsService()
	{
		return username -> userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
