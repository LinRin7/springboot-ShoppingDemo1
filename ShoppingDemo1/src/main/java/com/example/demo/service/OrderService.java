package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Order;

public interface OrderService {

	public List<Order> getOrderList();//尋找全部訂單
	
	public Order findOrderById(String id);//由order的id 尋找單筆訂單
	
	public List<Order> findAllByCustomerId(int id);//由order的customerId 尋找該顧客的所有訂單
}
