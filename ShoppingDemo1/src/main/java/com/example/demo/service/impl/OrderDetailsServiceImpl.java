package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	/*@Override
	public List<OrderDetails> findOrderDetailsByOrderId(String orderId) {//待修改
		//根據給定的ID集合查詢所有對應的實體
		List<String> list = new ArrayList<String>();
		list.add(orderId);
		return orderDetailsRepository.findAllById(null);
	}*/
	/*
	@Override
	public List<OrderDetails> findOrderDetailsByOrderId(String orderId) {
		return orderDetailsRepository.findAllByOrderIdIn(null);
	}
	
	@Override
	public List<Product> findProductsByProductId(List<String> list) {
		return orderDetailsRepository.findAllByProductIdIn(list);
	}
	*/
	

	
}
