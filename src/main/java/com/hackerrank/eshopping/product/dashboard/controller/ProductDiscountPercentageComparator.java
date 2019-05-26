package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.Comparator;

import com.hackerrank.eshopping.product.dashboard.model.Product;

public class ProductDiscountPercentageComparator implements Comparator<Product> {
	
	@Override
	public int compare(Product p1, Product p2) {
		int discountPercentage = getDiscountedPercentage(p2) - getDiscountedPercentage(p1);
		
		if(discountPercentage != 0) {
			return discountPercentage;
		} else {
			int discountPriceComparison = p2.getDiscountedPrice().compareTo(p1.getDiscountedPrice());
			
			if(discountPriceComparison != 0) {
				return discountPriceComparison;
			} else {
				return Math.toIntExact(p2.getId()) - Math.toIntExact(p1.getId());
			}
		}
	}
	
	private int getDiscountedPercentage(Product p) {
		Double num = p.getRetailPrice() - p.getDiscountedPrice();
		Double den = p.getRetailPrice();
		return (int)((num/den) * 100);
	}

}
