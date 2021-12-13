package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Member;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Product;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	//登入動作
	@PostMapping("login")
	@ResponseBody
	public String login(Member m, HttpSession session) {
		Member member = new Member();
		member = memberRepository.findByUsernameAndPassword(m.getUsername(), m.getPassword());
		if(member == null) {
			return "登入失敗";
		}else{
			session.setAttribute("user", member);
			return member.getName() + " 先生/小姐 歡迎您\n登入成功";
		}
	}
	
	//登出動作
	@GetMapping("logout")
	@ResponseBody
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "登出成功";
	}
	
	//前往註冊頁面
	@GetMapping("register")
	public String toRegisterPage(Member m, HttpSession session) {
		return "memberRegister";
	}
	
	@PostMapping("register")
	@ResponseBody
	public Map<String, String> register(Member m) {
		//Member member = new Member();
		Map<String, String> map = new HashMap<String, String>();
		if(memberRepository.existsByUsername(m.getUsername())) {
			map.put("msg", "帳號已被使用");
			map.put("result", "failed");
		}else if(memberRepository.existsByEmail(m.getEmail())){
			map.put("msg", "電子信箱已被使用");
			map.put("result", "failed");
		}else {
			memberRepository.save(m);
			map.put("msg", "註冊成功\n回首頁後重新登入");
			map.put("result", "success");	
		}
		return map;
	}
	
	//前往會員中心
	@GetMapping("memberCenter")
	public String memberCenter() {
		
		return "memberCenter";
	}
	
	//前往會員資料更改畫面
	@GetMapping("updateMemberDataPage")
	public String updateMemberDataPage() {
		return "memberUpdateData";
	}
	/*
	@PutMapping("updateMemberData")
	public String updateMemberData(Member member, HttpSession session) {
		member.setId(((Member)session.getAttribute("user")).getId());
		memberRepository.save(member);
		return "redirect:";
	}
	*/
	@PutMapping(value="updateMemberData")
	//@PatchMapping(value="updateMemberData")
	@ResponseBody
	public Map<String, String> updateMemberData(@RequestBody Member member, HttpSession session) {
		System.out.println(member.toString());
		member.setId(((Member)session.getAttribute("user")).getId());
		memberRepository.save(member);
		session.removeAttribute("user");
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "success");
		return map;
	}
	
	
	//前往會員訂單查詢
	@GetMapping("queryMemberOrderPage")
	public String queryMemberOrderPage(HttpSession session, Model model) {
		
		List<Order> list = new ArrayList<Order>();
		
		//System.out.println(orderRepository.findByMemberIdId(((Member)session.getAttribute("user")).getId()));
		list = orderRepository.findByMemberIdId(((Member)session.getAttribute("user")).getId());
		//list = orderRepository.findByMemberId((Member)session.getAttribute("user"));
		if(list.size()==0) {
			System.out.println("無購物");
		}else {
			System.out.println("有購物");
		}
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		model.addAttribute("list", list);
		//ModelAndView mav = new ModelAndView("memberQueryOrder");
		//mav.addObject("list", list);
		return "memberQueryOrder";
		
	}
	
	@PostMapping("queryMemberOrder/{id}")
	@ResponseBody
	public List<CartItem> queryMemberOrder(@PathVariable String id) {//方法1
		System.out.println("開始搜尋");
		System.out.println("開始搜尋orderDetails");
		List<OrderDetails> odl = new ArrayList<OrderDetails>();
		odl = orderDetailsRepository.findByOrderDetailsPKOrderIdId(id);
		System.out.println("orderDetails搜尋完畢");
		List<String> productIdList = new ArrayList<String>();
		System.out.println("開始放入porduct編號");
		for(int i=0; i<odl.size(); i++) {
			productIdList.add(odl.get(i).getOrderDetailsPK().getProductId().getId());
			System.out.print(i + ",");
		}
		List<Product> pl = new ArrayList<Product>();
		System.out.println("開始搜尋Product");
		pl = productRepository.findAllById(productIdList);
		System.out.println("開始放入已購入porduct，共有" + odl.size() + ":" + pl.size() + "個");
		List<CartItem> orderDetailsList = new ArrayList<CartItem>();
		for(int i=0; i<odl.size(); i++) {
			System.out.println(odl.get(i).toString());
			System.out.println(pl.get(i).toString());
			orderDetailsList.add(new CartItem(pl.get(i),odl.get(i).getQuantity()));
			System.out.println("購物車:" + orderDetailsList.get(i).getProduct().toString());
		}
		
		return orderDetailsList;
	}
	/*
	@PostMapping("queryMemberOrder/{id}")
	@ResponseBody
	public ModelAndView queryMemberOrder(@PathVariable String id, Model model) {//方法二
		System.out.println("開始搜尋");
		System.out.println("開始搜尋orderDetails");
		List<OrderDetails> odl = new ArrayList<OrderDetails>();
		odl = orderDetailsRepository.findByOrderDetailsPKOrderIdId(id);
		System.out.println("orderDetails搜尋完畢");
		List<String> productIdList = new ArrayList<String>();
		System.out.println("開始放入porduct編號");
		for(int i=0; i<odl.size(); i++) {
			productIdList.add(odl.get(i).getOrderDetailsPK().getProductId().getId());
			System.out.print(i + ",");
		}
		List<Product> pl = new ArrayList<Product>();
		System.out.println("開始搜尋Product");
		pl = productRepository.findAllById(productIdList);
		System.out.println("開始放入已購入porduct，共有" + odl.size() + ":" + pl.size() + "個");
		
		int total = 0;
		for(int i=0; i<odl.size(); i++) {
			total+=odl.get(i).getQuantity()*odl.get(i).getEachPrice();
		}
		ModelAndView mav = new ModelAndView("memberOrderDetails");
		mav.addObject("orderDetails", odl);
		mav.addObject("product", pl);
		mav.addObject("total", total);
		return mav;
	}*/
	
	
}
