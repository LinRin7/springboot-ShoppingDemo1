package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

	//Order findById(String id); //error 已經有方法在jpa了
	List<Order> findByMemberIdId(int id);//由order的memberId 尋找該顧客的所有訂單
	List<Order> findByMemberId(Member member);//由order的memberId 尋找該顧客的所有訂單
}
