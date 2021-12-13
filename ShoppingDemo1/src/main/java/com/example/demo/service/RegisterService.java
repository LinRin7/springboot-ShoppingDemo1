package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Member;

public interface RegisterService {

	public List<Member> getMemberList();
	
	public void saveMember(Member member);
}
