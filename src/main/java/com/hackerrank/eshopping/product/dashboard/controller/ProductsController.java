package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@RequestMapping(method=RequestMethod.GET)
	public List<Product> findAllProducts(){
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
	@RequestMapping(method=RequestMethod.GET, value="/{product_id}")
	public ResponseEntity<Product> findProductById(@PathVariable Long product_id){
		return productService.getProductById(product_id);
	}
	
	/*
	 * This method is used to get the products that match a particular category
	 */
	@RequestMapping(method=RequestMethod.GET, params= "category")
	@ResponseBody
	public ResponseEntity<List<Product>> findProductsByCategory( @RequestParam("category") String category){
		return productService.getProductByAvailability(category);
	}
	
	/*
	 * This method is used to get products based on category and availability
	 */
	@RequestMapping(method=RequestMethod.GET, params= {"category", "availability"})
	@ResponseBody
	public ResponseEntity<List<Product>> findProductsByCategoryAndAvailability(@RequestParam("category") String category,
			@RequestParam("availability") Integer availability){
		return productService.getProductByCategoryAndAvailability(availability, category);
	}
}

























