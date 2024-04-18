package com.nnaltakyan.core.auth.config.filter;

import com.nnaltakyan.core.auth.domain.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER = "Bearer ";

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
		throws ServletException, IOException
	{
		final String authorizationHeader = request.getHeader(AUTH_HEADER);
		if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(BEARER))
		{
			filterChain.doFilter(request, response);
			return;
		}
		final String jwt = authorizationHeader.substring(7);
		final String username = jwtService.extractUsername(jwt);
		if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.isTokenValid(jwt, userDetails))
			{
				var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
