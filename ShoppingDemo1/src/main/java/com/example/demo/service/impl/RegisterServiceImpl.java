package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public List<Member> getMemberList() {
		return memberRepository.findAll();
	}

	@Override
	public void saveMember(Member member) {
		memberRepository.save(member);
	}

	
}
