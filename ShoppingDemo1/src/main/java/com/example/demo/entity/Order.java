package com.example.demo.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Table(name="orders")
public class Order implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@CreatedDate//自動添加創建時間的註解，需在Application启动类中添加注解 @EnableJpaAuditing。
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_date")
	private Date orderDate;
	private String status;
	private String comment;
	@JsonBackReference(value="memberOrders")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", referencedColumnName="id")
	private Member memberId;
	//@JsonManagedReference(value="OrderDetailsPK")
	//@JsonManagedReference
	@OneToMany(mappedBy="orderDetailsPK.orderId", cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	private List<OrderDetails> orderdetails;
	
	public Order() {
		super();
	}

	public Order(String id, Date orderDate, String status, String comment, Member memberId) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
		this.comment = comment;
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setCreateDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Member getMemberId() {
		return memberId;
	}

	public void setMemberId(Member memberId) {
		this.memberId = memberId;
	}

	public List<OrderDetails> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(List<OrderDetails> orderdetails) {
		this.orderdetails = orderdetails;
	}


}
