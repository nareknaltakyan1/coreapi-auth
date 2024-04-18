package com.nnaltakyan.core.auth.domain.service.jwt;

import com.nnaltakyan.core.auth.domain.service.system.SystemDateTimeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService
{
	public static final String SECRET = "nnaltakyancoreapiauthnnaltakyancoreapiauthnnaltakyancoreapiauth";

	private final SystemDateTimeService systemDateTimeService;

	public String generateToken(final UserDetails userDetails)
	{
		return generateToken(Collections.emptyMap(), userDetails);
	}

	public String generateToken(final Map<String, Object> extraClaims, final UserDetails userDetails)
	{
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(systemDateTimeService.getCurrentDate())
			.setExpiration(systemDateTimeService.getCurrentDatePlusExtraTime(1000 * 60 * 24)).signWith(getSignKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isTokenValid(final String jwt, final UserDetails userDetails)
	{
		final String username = extractUsername(jwt);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
	}

	public String extractUsername(final String jwt)
	{
		return extractClaim(jwt, Claims::getSubject);
	}

	public <T> T extractClaim(final String jwt, final Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(final String jwt)
	{
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(jwt).getBody();
	}

	private Key getSignKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private boolean isTokenExpired(final String jwt)
	{
		return extractExpiration(jwt).before(systemDateTimeService.getCurrentDate());
	}

	private Date extractExpiration(final String jwt)
	{
		return extractClaim(jwt, Claims::getExpiration);
	}
}
