package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CartItem implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Product product;
	private int quantity;
	
	public CartItem() {
		super();
	}

	public CartItem(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return this.getProduct().toString() + ",數量:" + this.getQuantity();
	}
}
