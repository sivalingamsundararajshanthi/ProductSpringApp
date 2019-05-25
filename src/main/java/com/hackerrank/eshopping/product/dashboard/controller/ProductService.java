package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
	 * This method queries the database for all the products and appends them to a list and returns them as a ResponseEntity with
	 * a generic type of list of products.
	 * 
	 * The returned list is in ascending order of id
	 */
	public ResponseEntity<List<Product>> getAllProducts(){
		
		List<Product> products = new ArrayList<>();
		
		//Add all products from iterable
		productRepository.findAll().forEach(products::add);
		
		//Sort the products using id
		products.sort(Comparator.comparing(Product::getId));
		
		//return the response entity along with the Http response code
		return new ResponseEntity<>(products, HttpStatus.OK);
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
	
	/**
	 * This method is used to update a product in the database.
	 * 
	 * This method will check to see if the product exists in the database with the given id-
	 * (i)If the product exists in the database-
	 * 		(a)It will check if retail_price, discounted_price and availability have the same value, if so it won't update and
	 *         return Http response code of 400 else it will update and give Http response code of 200.
	 * (ii)If the product does not exist it will return a Http response code of 400.
	 */
	public ResponseEntity<?> updateProduct(Product product) {
		if(productRepository.existsById(product.getId())) {
			
			//The product exists get Optional
			Optional<Product> p = productRepository.findById(product.getId());
			if(p.isPresent()) {
				//Get product object from the optional
				Product pr = p.get();
				
				if((pr.getRetailPrice() == product.getRetailPrice()) && (pr.getDiscountedPrice() == product.getDiscountedPrice())
						&& (pr.getAvailability() == product.getAvailability())) {
					//If all the three values in the JSON match the fields of the object don't update 
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				} else {
					//None of the values match update the value
					productRepository.save(product);
					//Return OK HttStatus Response
					return new ResponseEntity<>(HttpStatus.OK);
				}
			} else {
				//Optional is empty send BAD_REQUEST HttpRequest Response
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			//Optional is empty send BAD_REQUEST HttpRequest Response
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * This method is used to get product from the database based on the ID.
	 * 
	 * (i)If the a product is found which has the id that is passed in, return the ResponseEntity with the product and 200
	 *    HttpResponse code.
	 * (ii)Else return ResponseEntity with 404 HttpResponse code.
	 */
	public ResponseEntity<Product> getProductById(Long id){
		if(productRepository.existsById(id)) {
			//Product is found
			return new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK);
		} else {
			//Product is not found
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}























