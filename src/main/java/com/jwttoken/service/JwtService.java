package com.jwttoken.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtService {

	public String generateToken(String username) {
		Map<String, Object> claims=new HashMap<>();
		return createToken(claims,username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+5*60*60*1000))
				.signWith(getsignKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getsignKey() {
		byte[] keybytes=Decoders.BASE64.decode("66F618D9275434FD786949F5CE7B682vdjfnwe28ey82731");
		return Keys.hmacShaKeyFor(keybytes);
	}

	public String extractUsername(String token) {
      return extractClaim(token,Claims::getSubject);		
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return  Jwts
				.parserBuilder()
				.setSigningKey(getsignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
	  final String username=extractUsername(token);
	  boolean flag=username.equals(userDetails.getUsername()) && !isTokenExprired(token);
	  System.out.println("flag : " + flag);
	  return (username.equals(userDetails.getUsername()) && !isTokenExprired(token));
	}

	private Boolean isTokenExprired(String token) { 
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}


}
