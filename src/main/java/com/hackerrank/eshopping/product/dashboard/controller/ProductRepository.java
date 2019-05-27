package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackerrank.eshopping.product.dashboard.model.Product;

/*
 * Make the interface extend from CrudRepository such that it has pre defined functions for us to do CRUD operations.
 * Giving Product as one of the generic type and Long as the second generic type since the primary key of Product is of datatype 
 * Long
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	//This method signature will form the query to get all the products that match the passed in category String
	public List<Product> findByCategory(String category);
	
	//This method signature will form the query to get all the products that have the passed in category and availability
	public List<Product> findByCategoryAndAvailability(String category, Boolean availability);
}
