package com.jwttoken.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwttoken.entity.UserInfo;

public class UserInfoUserDetail implements UserDetails{

	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetail(UserInfo userInfo) {
		username=userInfo.getUsername();
		password=userInfo.getPassword();
	    authorities=Arrays.stream(userInfo.getRoles().split(","))
	    		.map(SimpleGrantedAuthority::new)
	    		.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}
