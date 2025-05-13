package com.kamael.nplp_api.configuration;

import java.io.Console;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${app.secret-key}")
	private String secretKey;
	
	@Value("${app.expiration-time}")
	private String expirationTime;
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		
		return createToken(username, claims);
	}
	
	public String createToken(String subject, Map<String, Object> claims) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 4 * 60 * 60 * 1000);
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = secretKey.getBytes();
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpirationDate(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getSignKey())
				.parseClaimsJws(token)
				.getBody();
	}
}
