package com.jwttoken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwttoken.entity.UserInfo;
import com.jwttoken.service.UserInfoService;

@RestController
@RequestMapping("user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/saveUserInfo")
	public Object saveUserInfo(@RequestBody UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		return userInfoService.saveUserInfo(userInfo);
	}
}
