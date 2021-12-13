package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/* 參考:https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
 * Could not write JSON: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.SerializationFeature.FAIL_ON_EMPTY_BEANS) )
 * */
@EntityListeners(AuditingEntityListener.class)
@Table(name="products")
public class Product implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private String publisher;
	private String type;
	private String description;
	private String image;
	private int price;
	@CreatedDate//自動添加創建時間的註解
	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;
	@Column(name="create_time_string")
	private String createTimeString;
	//@JsonManagedReference(value="OrderDetailsPK")
	//@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="orderDetailsPK.productId", fetch=FetchType.LAZY)
	private List<OrderDetails> orderdetails;
	
	
	
	
	public Product() {
		super();
	}

	public Product(String id, String name, String publisher, String type, String description, String image, int price,
			Date createTime, String createTimeString) {
		super();
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.type = type;
		this.description = description;
		this.image = image;
		this.price = price;
		this.createTime = createTime;
		this.createTimeString = createTimeString;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public List<OrderDetails> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(List<OrderDetails> orderdetails) {
		this.orderdetails = orderdetails;
	}
	

}
