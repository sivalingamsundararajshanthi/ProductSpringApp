package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.Comparator;

import com.hackerrank.eshopping.product.dashboard.model.Product;

/*
 * This class implements the Comaparator and it is used to sort the list of Products such that it satisfies "Return Products By
 * Category and availability"
 */
public class ProductDiscountPercentageComparator implements Comparator<Product> {
	
	/**
	 * Overriden compare method which compares Product P1 and Product P2
	 */
	@Override
	public int compare(Product p1, Product p2) {
		int discountPercentage = getDiscountedPercentage(p2) - getDiscountedPercentage(p1);
		
		if(discountPercentage != 0) {
			return discountPercentage;
		} else {
			int discountPriceComparison = Double.compare(p1.getDiscountedPrice(), p2.getDiscountedPrice());
			
			if(discountPriceComparison != 0) {
				return discountPriceComparison;
			} else {
				return Math.toIntExact(p1.getId()) - Math.toIntExact(p2.getId());
			}
		}
	}
	
	/*
	 * This function is used to get the discount percentage of the particular product
	 */
	private int getDiscountedPercentage(Product p) {
		Double num = p.getRetailPrice() - p.getDiscountedPrice();
		Double den = p.getRetailPrice();
		Double div = num/den;
		return (int) Math.round(div * 100);
	}

}
