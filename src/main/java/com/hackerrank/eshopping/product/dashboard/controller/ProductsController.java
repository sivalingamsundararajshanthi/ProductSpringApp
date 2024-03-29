package com.hackerrank.eshopping.product.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.model.Product;

/*
 * This class is the Rest controller which maps the request URL to corresponding methods.
 */
@RestController
public class ProductsController {

	//Autowiring the ProductService object
	@Autowired
	private ProductService productService;
	
	/*
	 * This service method is used to get all the products in the database and return it with the response code
	 */
	@RequestMapping(method=RequestMethod.GET, value="/products")
	@ResponseBody
	public List<Product> findAllProducts(){
		return productService.getAllProducts();
	}
	
	/*
	 * This service method is used to add a product to the database
	 */
	@RequestMapping(method=RequestMethod.POST, value="/products")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	/*
	 * This service method is used to update a value in the database
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/products/{product_id}")
	public ResponseEntity<?> updateProduct(@RequestBody Map<String, Object> payLoad, @PathVariable("product_id") Long product_id) {
		return productService.updateProduct(product_id, payLoad);
	}
	
	/*
	 * This method is used to return a Product if it exists along with a ResponseEntity
	 */
	@RequestMapping(method=RequestMethod.GET, value="/products/{product_id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> findProductById(@PathVariable("product_id") Long product_id){
		return productService.getProductById(product_id);
	}
	
	/*
	 * This method is used to get the products that match a particular category
	 */
	@RequestMapping(method=RequestMethod.GET, params="category", value="/products")
	@ResponseBody
	public ResponseEntity<List<Product>> findProductsByCategory(@RequestParam("category") String category){
		return productService.getProductByAvailability(category);
	}
	
	/*
	 * This method is used to get products based on category and availability
	 */
	@RequestMapping(method=RequestMethod.GET, params= {"category", "availability"}, value="/products")
	@ResponseBody
	public ResponseEntity<List<Product>> findProductsByCategoryAndAvailability(@RequestParam("category") String category,
			@RequestParam("availability") Integer availability){
		String urlString = category;
		try {
			urlString = URLDecoder.decode(category, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productService.getProductByCategoryAndAvailability(availability, urlString);
	}
}