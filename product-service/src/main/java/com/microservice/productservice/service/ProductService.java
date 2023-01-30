package com.microservice.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.productservice.dto.ProductRequest;
import com.microservice.productservice.dto.ProductResponse;
import com.microservice.productservice.model.Product;
import com.microservice.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private  ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();

		productRepository.save(product);
		log.info("product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		
		List<Product> products = productRepository.findAll();
		
		return products.stream().map(this::mapToProductRepsonse).collect(Collectors.toList());
	}

	private ProductResponse mapToProductRepsonse(Product product) {
		// TODO Auto-generated method stub
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
