package com.jwttoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwttoken.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByProductName(String productName);

	Optional<Product> findByProductName(String productName);

}
