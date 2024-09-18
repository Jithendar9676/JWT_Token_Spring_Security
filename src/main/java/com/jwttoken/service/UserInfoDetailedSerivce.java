package com.jwttoken.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwttoken.dto.UserInfoUserDetail;
import com.jwttoken.entity.UserInfo;
import com.jwttoken.repository.UserInfoRepository;

@Service
public class UserInfoDetailedSerivce implements UserDetailsService {

	@Autowired
	private UserInfoRepository userInfoRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userinfo=userInfoRepository.findByUsername(username);
		return userinfo.map(UserInfoUserDetail::new).orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}

}
