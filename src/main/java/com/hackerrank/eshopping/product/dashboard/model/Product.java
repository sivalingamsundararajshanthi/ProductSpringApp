package com.hackerrank.eshopping.product.dashboard.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


/*
 * Making the Product class as an Entity such that the Product object can be mapped to Database.
 */
@Entity
public class Product implements Serializable {
	
	@Id
    private Long id;
    private String name;
    private String category;
    private Double retail_price;
    private Double discounted_price;
    private Boolean availability;

    public Product() {
    }

    public Product(Long id, String name, String category, Double retailPrice, Double discountedPrice, Boolean availability) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.retail_price = retailPrice;
        this.discounted_price = discountedPrice;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getRetailPrice() {
        return retail_price;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retail_price = retailPrice;
    }

    public Double getDiscountedPrice() {
        return discounted_price;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discounted_price = discountedPrice;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
