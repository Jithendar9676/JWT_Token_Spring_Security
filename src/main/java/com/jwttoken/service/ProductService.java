package com.jwttoken.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jwttoken.entity.Product;
import com.jwttoken.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public Object addProduct(Product product) {
		if(productRepository.existsByProductName(product.getProductName())) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product name already exits");
		}
		try {
			Product newProduct=productRepository.save(product);
			System.out.println("newProduct : " + newProduct);
			return  ResponseEntity.status(HttpStatus.OK).body("product Saved");
		}catch (Exception e) {
	       e.printStackTrace();
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Soming went worng");
		}
	}

	public Object getProductByName(String productName) {
		Optional<Product> product=productRepository.findByProductName(productName);
		if(product.isEmpty()) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is empty");
		}else {
			return  ResponseEntity.status(HttpStatus.OK).body(product);
		}
	}
	public ResponseEntity<Object> getAllProducts(){
		List<Product> products=productRepository.findAll();
		if(products.isEmpty()) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no products are avaliable");
		}else {
			return  ResponseEntity.status(HttpStatus.OK).body(products);
		}
	}
	
	public ResponseEntity<Object> updateProduct(Long id, Product product){
		Optional<Product> product1=productRepository.findById(id);
		if(product1.isEmpty()) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not present");
		}else {
			Product updateProduct=product1.get();
			updateProduct.setProductName(product.getProductName());
			updateProduct.setPrice(product.getPrice());
			updateProduct.setDepartment(product.getDepartment());
			productRepository.save(updateProduct);
			return ResponseEntity.status(HttpStatus.OK).body(updateProduct);	
		}
	}
	
	public ResponseEntity<Object> deleteById(Long id){
		if(productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Product is deleted ");
		}else {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not present");
		}
	}
}
