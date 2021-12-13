package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Embeddable
public class OrderDetailsPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Id
	//@JsonBackReference(value="orderId")
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id", referencedColumnName="id")
	private Order orderId;
	//@Id
	//@JsonBackReference(value="productId")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="product_id", referencedColumnName="id")
	private Product productId;
	
	
	
	public OrderDetailsPK() {
		super();
	}



	public OrderDetailsPK(Order orderId, Product productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
	}


	

	public Order getOrderId() {
		return orderId;
	}



	public void setOrderId(Order orderId) {
		this.orderId = orderId;
	}



	public Product getProductId() {
		return productId;
	}



	public void setProductId(Product productId) {
		this.productId = productId;
	}


	
}
