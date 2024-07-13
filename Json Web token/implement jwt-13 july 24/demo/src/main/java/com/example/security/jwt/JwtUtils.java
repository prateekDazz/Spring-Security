package com.example.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;

	@Value("${spring.app.jwtExpirationMs}")
	private int jwtExpirationMs;

//	This method is used to extract tokken form the authorization header and returns the token string 
	public String getJwtFromHeader(HttpServletRequest request) {
		logger.debug("calling getJwtFromHeader() {}");
		String bearerToken = request.getHeader("Authorization");
		logger.debug("Authorization Header:{} " + bearerToken);
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public String generateTokenFromUserName(UserDetails userDetails) {
		logger.debug("calling generateTokenFromUserName() {}");
		String userName = userDetails.getUsername();
		return Jwts.builder().setSubject(userName).setIssuedAt(new Date()).signWith(key())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUserNameFromJwtToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			logger.info("inside validateJwtToken() {}");

			Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.error("Exception caught in validateJwtToken() {}" + e.getMessage());
		}
		return false;
	}

}
