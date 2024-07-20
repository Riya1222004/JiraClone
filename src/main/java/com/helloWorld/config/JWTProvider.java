package com.helloWorld.config;


import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTProvider {
	static SecretKey key=Keys.hmacShaKeyFor(JWTConstant.SECRETE_KEY.getBytes());
	/*Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwt)
            .getBody();
	String emailString=String.valueOf(claims.get("email"));
	String authoritieString=String.valueOf(claims.get("authorities"));*/
	public static String genrateToken(Authentication auth) {
		String jwt=Jwts.builder().setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", auth.getName())
				.signWith(key)
				.compact();
		return jwt;
	}
	public static String getEmailFromToken(String jwt) {
		Claims claims = Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(jwt)
	            .getBody();
		String emailString=String.valueOf(claims.get("email"));
		return emailString;
	}
}
