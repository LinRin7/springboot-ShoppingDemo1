package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.LoginService;

//編寫service層實現類
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private MemberRepository memberRepository;//DAO
	
	@Override
	public List<Member> getMemberList() {
		return memberRepository.findAll();
	}

	@Override
	public Member findMember(String username, String password) {
		return memberRepository.findByUsernameAndPassword(username, password);
	}

}
