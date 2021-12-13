package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.OrderDetailsPK;
import com.example.demo.entity.Product;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsPK>{
	
	
	List<OrderDetails> findByOrderDetailsPKOrderIdId(String orderId);
	
	/*
	//JPA命名規則 若參數是放入List 名稱要加上In
	List<Order> findByOrderId(String orderId);
	
	//JPA命名規則 若參數是放入List 名稱要加上In
	List<Product> findAllByProductIdIn(List<String> productIdList);
	*/
}
