package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orderdetails")
//@NamedQuery(name="OrderDetails.findAll", query="select o from OrderDetails o")
public class OrderDetails implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	//@JsonBackReference(value="OrderDetailsPK")
	//@JsonBackReference
	private OrderDetailsPK orderDetailsPK;
	
	private int quantity;
	private int eachPrice;
	
	
	public OrderDetails() {
		super();
	}
	
	public OrderDetails(OrderDetailsPK orderDetailsPK, int quantity, int eachPrice) {
		this.orderDetailsPK = orderDetailsPK;
		this.quantity = quantity;
		this.eachPrice = eachPrice;
	}

	public OrderDetails(Order orderId, Product productId, int quantity, int eachPrice) {
		super();
		this.orderDetailsPK = new OrderDetailsPK(orderId, productId);
		this.quantity = quantity;
		this.eachPrice = eachPrice;
	}


	public OrderDetailsPK getOrderDetailsPK() {
		return orderDetailsPK;
	}

	public void setOrderDetailsPK(OrderDetailsPK orderDetailsPK) {
		this.orderDetailsPK = orderDetailsPK;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getEachPrice() {
		return eachPrice;
	}

	public void setEachPrice(int eachPrice) {
		this.eachPrice = eachPrice;
	}
	


	
}
