package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.Comparator;

import com.hackerrank.eshopping.product.dashboard.model.Product;

public class ProductDiscountPercentageComparator implements Comparator<Product> {
	
	@Override
	public int compare(Product p1, Product p2) {
		return getDiscountedPercentage(p2) - getDiscountedPercentage(p1);
	}
	
	private int getDiscountedPercentage(Product p) {
		return (int)(((p.getRetailPrice()-p.getDiscountedPrice())/p.getRetailPrice()) * 100);
	}

}
