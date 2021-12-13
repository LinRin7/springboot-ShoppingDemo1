package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Member;

//編寫service層介面
public interface LoginService {

	public List<Member> getMemberList();
	
	public Member findMember(String username, String password);
}
