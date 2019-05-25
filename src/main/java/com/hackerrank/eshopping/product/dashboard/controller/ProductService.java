package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackerrank.eshopping.product.dashboard.model.Product;

/**
 * @author ss siva 
 * This is the Service class that interacts with the database and retrieves corresponding data from the database.
 */
@Service
public class ProductService {
	
	//Autowiring such that the dependency will be injected
	@Autowired
	private ProductRepository productRepository;
	
	/*
	 * This method queries the database for all the products and appends them to a list and returns them as a list
	 * 
	 * Needs work
	 */
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
	
	/*
	 * This method is used to add a product to the database.
	 * 
	 * This method will check to see if a product exists with same id-
	 * (i)If a product exists with the same id then it will return a HTTPResponse code of 400
	 * (ii)If not it will return a HTTPResponse of 201
	 */
	public ResponseEntity addProduct(Product product) {
		
		//Check to see if the passed in product exists in the Database
		if(productRepository.existsById(product.getId())) {
			//Product exists return a HTTP response code of 400
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} else {
			//Product does not exist, save the product onto the database.
			productRepository.save(product);
			
			//Return HTTPResponse code of 201
			return new ResponseEntity(HttpStatus.CREATED);
		}
	}
}























