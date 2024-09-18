package com.jwttoken.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwttoken.service.JwtService;
import com.jwttoken.service.UserInfoDetailedSerivce;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter  {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserInfoDetailedSerivce userInfoDetailedSerivce;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header =request.getHeader("Authorization");
		String username=null;
		String token=null;
		if(null !=header && header.startsWith("Bearer ")) {
			token=header.substring(7);
			username=jwtService.extractUsername(token);
		}
		if(null !=username && SecurityContextHolder.getContext().getAuthentication() ==null) {
			UserDetails userDetails=userInfoDetailedSerivce.loadUserByUsername(username);
			if(jwtService.validateToken(token,userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			    usernamePasswordAuthenticationToken.setDetails(userDetails);
			    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
