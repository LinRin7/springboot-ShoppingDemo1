package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

//DAO層
//實現資料持久層
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member>{
	//須根據JPA命名規則
	Member findByUsernameAndPassword(String username, String password);//尋找單個
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	List<Member> findByNameLike(String name);
	
	//void saveMember(Member member);//註冊成功，存入會員
}
