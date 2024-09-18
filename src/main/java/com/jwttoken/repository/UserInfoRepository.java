package com.jwttoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwttoken.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	boolean existsByUsername(String username);

	Optional<UserInfo> findByUsername(String username);

}
