package com.jwttoken.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jwttoken.entity.UserInfo;
import com.jwttoken.repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public Object saveUserInfo(UserInfo userInfo) {
		if(userInfoRepository.existsByUsername(userInfo.getUsername())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already exits");
		}try {
			UserInfo newuserInfo=userInfoRepository.save(userInfo);
			return ResponseEntity.status(HttpStatus.CREATED).body("User created");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went worng");
		}
	}
}
