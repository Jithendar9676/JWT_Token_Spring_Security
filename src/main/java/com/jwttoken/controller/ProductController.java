package com.jwttoken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwttoken.entity.Product;
import com.jwttoken.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("Product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	@PostMapping("/addProduct")
	public Object addProduct(@Valid @RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@GetMapping("/getProductByName")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public Object getProductByName(@Valid @RequestParam String productName) {
		return productService.getProductByName(productName);
	}
	
	@GetMapping("/getAllProducts")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@PutMapping("/updateProduct/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> updateProduct(@Valid @PathVariable Long id,@RequestBody Product product){
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> deleteProduct(@Valid @PathVariable Long id){
		return productService.deleteById(id);
	}
}
