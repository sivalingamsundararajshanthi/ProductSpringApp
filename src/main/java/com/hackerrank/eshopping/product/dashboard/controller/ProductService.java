package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

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
	 
	public ResponseEntity<List<Product>> getAllProducts(){
		
		List<Product> products = new ArrayList<>();
		
		//Add all products from iterable
		productRepository.findAll().forEach(products::add);
		
		//Sort the products using id
		products.sort(Comparator.comparing(Product::getId));
		
		//return the response entity along with the Http response code
		return new ResponseEntity<>(products, HttpStatus.OK);
	}*/
	
	public List<Product> getAllProducts(){
		
		List<Product> products = new ArrayList<>();
		
		//Add all products from iterable
		productRepository.findAll().forEach(products::add);
		
		//Sort the products using id
		products.sort(Comparator.comparing(Product::getId));
		
		//return the response entity along with the Http response code
		return products;
	}
	
	/*
	 * This method is used to add a product to the database.
	 * 
	 * This method will check to see if a product exists with same id-
	 * (i)If a product exists with the same id then it will return a HTTPResponse code of 400
	 * (ii)If not it will return a HTTPResponse of 201
	 */
	public ResponseEntity<?> addProduct(Product product) {
		
		//Check to see if the passed in product exists in the Database
		if(productRepository.existsById(product.getId())) {
			//Product exists return a HTTP response code of 400
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			//Product does not exist, save the product onto the database.
			productRepository.save(product);
			
			//Return HTTPResponse code of 201
			return new ResponseEntity<>(HttpStatus.CREATED);
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
	public ResponseEntity<?> updateProduct(Long product_id, Map<String, Object> payLoad) {
		/*
		if(productRepository.findById(product_id) != null) {
			return new ResponseEntity<>("No Such Product", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("No Such Product", HttpStatus.BAD_REQUEST);
		}*/
		
		if(productRepository.existsById(product_id)) {
			
			//The product exists get Optional
			Optional<Product> p = productRepository.findById(product_id);
			if(p.isPresent()) {
				//Get product object from the optional
				Product pr = p.get();
				
				if((pr.getRetailPrice() == payLoad.get("retail_price")) 
						&& (pr.getDiscountedPrice() == payLoad.get("discounted_price"))
						&& (pr.getAvailability() == payLoad.get("availability"))) {
					//If all the three values in the JSON match the fields of the object don't update 
					return new ResponseEntity<>("No Such Product", HttpStatus.BAD_REQUEST);
				} else {
					//None of the values match update the value
					pr.setAvailability((Boolean)payLoad.get("availability"));
					pr.setDiscountedPrice((Double)payLoad.get("discounted_price"));
					pr.setRetailPrice((Double)payLoad.get("retail_price"));
					productRepository.save(pr);
					//Return OK HttStatus Response
					return new ResponseEntity<>("Success", HttpStatus.OK);
				}
			} else {
				//Optional is empty send BAD_REQUEST HttpRequest Response
				return new ResponseEntity<>("No Such Product", HttpStatus.BAD_REQUEST);
			}
		} else {
			//Optional is empty send BAD_REQUEST HttpRequest Response
			return new ResponseEntity<>("No Such Product", HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * This method is used to get product from the database based on the ID.
	 * 
	 * (i)If the a product is found which has the id that is passed in, return the ResponseEntity with the product and 200
	 *    HttpResponse code.
	 * (ii)Else return ResponseEntity with 404 HttpResponse code.
	 */
	public ResponseEntity<?> getProductById(Long id){
		if(productRepository.existsById(id)) {
			//Product is found
			Product product = productRepository.findById(id).get();
			Double dis = product.getDiscountedPrice();
			Double ret = product.getRetailPrice();
			
			return new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK);
		} else {
			//Product is not found
			String errorMessage= "No such product found";
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * This method is used to get all the products based on the category that was passed in.
	 * (i)First items are categorized based on the availability.
	 * (ii)Second if objects have same availability, then they are sorted in the ascending order of discounted price.
	 * (iii)Third if objects have same discounted price they are sorted in the ascending order of id
	 */
	public ResponseEntity<List<Product>> getProductByAvailability(String category){
		List<Product> products = productRepository.findByCategory(category);
		products.sort(Comparator.comparing(Product::getAvailability).reversed().thenComparing(Product::getDiscountedPrice)
				.thenComparing(Product::getId));
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	/*
	 * This method is used to get all the products based on the category and availability that were passed in.
	 * (i)After this the list is sorted by the discount percentage in the descending order
	 * (ii)The products with the same discount percentage are sorted by discounted price in the ascending order
	 * (iii)The products with the same discounted price are sorted by ID in the ascending order
	 */
	public ResponseEntity<List<Product>> getProductByCategoryAndAvailability(Integer availability, String category){
		boolean con = (availability == 0) ? false : true;
		
		List<Product> products = productRepository.findByCategoryAndAvailability(category, con);
		
//		for(Product p : products) {
//			p.setDiscountedPercentage((int) ((p.getRetailPrice() - p.getDiscountedPrice())/p.getRetailPrice()) * 100);
//		}
		
//		products.sort(Comparator.comparing(Product::getDiscountedPercentage).reversed().thenComparing(Product::getDiscountedPrice)
//				.thenComparing(Product::getId));
		
		products.sort(new ProductDiscountPercentageComparator().thenComparing(Product::getDiscountedPrice)
				.thenComparing(Product::getId));
		
//		products.sort(new ProductDiscountPercentageComparator());
		
//		products.sort(Comparator.comparing().thenComparing());
		
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}























