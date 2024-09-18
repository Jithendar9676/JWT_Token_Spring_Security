package com.jwttoken.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "proudct")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	@NotEmpty(message = "Product Name should not be empty")
	@NotNull(message = "Product Name should not be null")
	@NotBlank(message = "Product Name should not be black")
	@Column(nullable = false)
	private String productName;

    @NotNull(message = "Price should not be null")
    @Min(value = 100, message = "Price should not be less than 100")
    @Max(value = 100000, message = "Price should not be more than 100000")
	private double price;
	@NotEmpty(message = "Department should not be empty")
	@NotNull(message = "Department should not be null")
	@NotBlank(message = "Department Name should not be black")
	private String department;

	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
//	@Override
//	public String toString() {
//		return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price + ", department="
//				+ department + "]";
//	}
	
	
}
