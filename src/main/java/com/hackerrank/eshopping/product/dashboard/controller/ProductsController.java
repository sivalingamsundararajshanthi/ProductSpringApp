package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.model.Product;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

	@Autowired
	private ProductService productService;
	
	/*
	 * This service method is used to get all the products in the database and return it with the response code
	 */
	public ResponseEntity<List<Product>> getAllProducts(){
		return productService.getAllProducts();
	}
	
	/*
	 * This service method is used to add a product to the database
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	/*
	 * This service method is used to update a value in the database
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/{product_id}")
	public void updateProduct(@RequestBody Product product, @PathVariable Long product_id) {
		productService.updateProduct(product);
	}
	
	/*
	 * This method is used to return a Product if it exists along with a ResponseEntity
	 */
	@RequestMapping("/{product_id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long product_id){
		return productService.getProductById(product_id);
	}
	
	/*
	 * This method is used to get the products that match a particular category
	 */
	@RequestMapping("?category={category}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category){
		return productService.getProductByAvailability(category);
	}
}

























