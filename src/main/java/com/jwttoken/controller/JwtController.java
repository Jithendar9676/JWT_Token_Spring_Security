package com.jwttoken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwttoken.dto.JwtRequest;
import com.jwttoken.dto.JwtRespones;
import com.jwttoken.service.JwtService;
import com.jwttoken.service.UserInfoDetailedSerivce;

@RestController
@RequestMapping("Jwt")
public class JwtController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserInfoDetailedSerivce userInfoDetailedSerivce;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/generateToken")
	public ResponseEntity<Object> generateToken(@RequestBody JwtRequest jwtRequest) {
		System.out.println("generateToken");
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		System.out.println("authentication : " + authentication );
		if(authentication.isAuthenticated()) {
			String token=jwtService.generateToken(jwtRequest.getUsername());
			System.out.println("token :" + token);
			JwtRespones respone=new JwtRespones();
			respone.setToken(token);
			respone.setUsername(jwtRequest.getUsername());
			return new ResponseEntity<>(respone,HttpStatus.OK);
		}else {
//			throw new UsernameNotFoundException("Invalid user");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid user");
		}
		
	}
}
