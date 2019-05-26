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
			int discountPriceComparison = Double.compare(p1.getDiscountedPrice(), p2.getDiscountedPrice());
			
			if(discountPriceComparison != 0) {
				return discountPriceComparison;
			} else {
				return Math.toIntExact(p1.getId()) - Math.toIntExact(p2.getId());
			}
		}
		
//		return getDiscountedPercentage(p2) - getDiscountedPercentage(p1);
	}
	
	private int getDiscountedPercentage(Product p) {
		Double num = p.getRetailPrice() - p.getDiscountedPrice();
		Double den = p.getRetailPrice();
		Double div = num/den;
		return (int) Math.round(div);
	}

}
