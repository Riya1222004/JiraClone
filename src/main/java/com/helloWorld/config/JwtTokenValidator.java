package com.helloWorld.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
			
		String jwt=request.getHeader(JWTConstant.JWT_HEADER);
		if(jwt!=null) {
			jwt=jwt.substring(7);
			try {
				SecretKey key=Keys.hmacShaKeyFor(JWTConstant.SECRETE_KEY.getBytes());
				Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
				String emailString=String.valueOf(claims.get("email"));
				String authoritieString=String.valueOf(claims.get("authorities"));
				List<GrantedAuthority> authorities=AuthorityUtils.commaSeparatedStringToAuthorityList(authoritieString);
				Authentication authentication = new UsernamePasswordAuthenticationToken(emailString, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadCredentialsException("invalid token riya");
			}
		}
		filterChain.doFilter(request, response);
		
	
	}

	

}
