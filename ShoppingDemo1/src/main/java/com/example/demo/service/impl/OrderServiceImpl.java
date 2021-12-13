package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<Order> getOrderList() {//尋找全部訂單
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findAllByCustomerId(int id) {//由order的customerId 尋找該顧客的所有訂單
		//根據給定的ID集合查詢所有對應的實體 可以搜尋多個ID
		List<Integer> list = new ArrayList<Integer>();
		list.add(id);
		return orderRepository.findByMemberIdId(id);
		
	}
	
	@Override
	public Order findOrderById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
