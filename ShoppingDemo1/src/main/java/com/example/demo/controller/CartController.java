package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Member;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.OrderDetailsPK;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@Controller
@RequestMapping("product")
public class CartController {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	/*@GetMapping("/")
	public String productHomePage() {

		return "productPage";
	}*/
	
	/*
	@RequestMapping("/")
	public List<Product> homePage(int page, int size){
		
		//菜鳥工程師 豬肉 Spring Data JPA 分頁及排序查詢範例
		Page<Product> pageResult = productRepository.findAll(
				PageRequest.of(page,//查詢的頁數，從0開始
								size,//查詢的每頁筆數
								Sort.by("createTimeString").descending()//依create_time_string欄位降冪
						));
		pageResult.getNumberOfElements();//本頁筆數
		pageResult.getSize();//每頁筆數
		pageResult.getTotalElements();//全部筆數
		pageResult.getTotalPages();//全部頁數
		
		List<Product> productlist = pageResult.getContent();
		return productlist;
	}*/
	
	
	@RequestMapping("")
	public ModelAndView productListPage(@RequestParam(value="start", defaultValue="0")Integer start,
									@RequestParam(value="limit", defaultValue="5")Integer limit,
									HttpSession session) {

		start = start < 0 ? 0 : start;
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		//Sort sort = Sort.by("createTimeString").descending()//依create_time_string欄位降冪
		Pageable pageable = PageRequest.of(start, limit, sort);
		Page<Product> page = productRepository.findAll(pageable);
		
		//page.getNumberOfElements();//本頁筆數
		//page.getSize();//每頁筆數
		//page.getTotalElements();//全部筆數
		//page.getTotalPages();//全部頁數
		//List<Product> productlist = page.getContent();

		return new ModelAndView("productPage", "page", page);
	}
	
	/*
	@RequestMapping("productList")
	@ResponseBody
	public Page<Product> productList(@RequestParam(value="start", defaultValue="0")Integer start,
									@RequestParam(value="limit", defaultValue="5")Integer limit,
									HttpSession session) {

		start = start < 0 ? 0 : start;
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		//Sort sort = Sort.by("createTimeString").descending()//依create_time_string欄位降冪
		Pageable pageable = PageRequest.of(start, limit, sort);
		Page<Product> page = productRepository.findAll(pageable);
		
		//page.getNumberOfElements();//本頁筆數
		//page.getSize();//每頁筆數
		//page.getTotalElements();//全部筆數
		//page.getTotalPages();//全部頁數
		//List<Product> productlist = page.getContent();
		
		return page;
	}
	*/
	@GetMapping("cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("buy/{id}")
	public String toCart(@PathVariable("id") String id, HttpSession session) {
		List<CartItem> cart;
		if(session.getAttribute("cart") == null) {
			cart = new ArrayList<CartItem>();
			cart.add(new CartItem(productRepository.findById(id).get(), 1));
			session.setAttribute("cart", cart);
		}else {
			cart = (List<CartItem>)session.getAttribute("cart");
			int index = this.exists(id, cart);
			if(index == -1) {
				cart.add(new CartItem(productRepository.findById(id).get(), 1));
			}else {
				int quantity = cart.get(index).getQuantity()+1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("cart", cart);
		}	
		session.setAttribute("total", calculateTotal(cart));
		return "cart";
	}
	
	@GetMapping(value="remove/{id}")
	public String remove(@PathVariable("id") String id, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		int index = this.exists(id, cart);
		cart.remove(index);
		session.setAttribute("cart", cart);
		session.setAttribute("total", calculateTotal(cart));
		return "cart";
	}
	
	
	public int exists(String id, List<CartItem> cart) {
		for(int i=0; i<cart.size(); i++) {
			if(cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}
	
	public int calculateTotal(List<CartItem> cart) {
		int total = 0;
		for(CartItem c : cart) {
			total += c.getProduct().getPrice() * c.getQuantity();
		}
		return total;
	}
	
	@GetMapping("cart/checkout")
	public String checkout(HttpSession session) {
		
		return "checkoutPage1";
	}
	
	@GetMapping("orderDecide")
	public String orderDecide(HttpSession session) {
		Order order = new Order();
		List<OrderDetails> odl = new ArrayList<OrderDetails>();
		Member member = ((Member)session.getAttribute("user"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
		order.setId(dtf.format(LocalDateTime.now()) + "_" + member.getId());
		order.setMemberId(member);
		//@SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>)session.getAttribute("cart");
		//System.out.println(cart.get(0).getProduct());
		for(int i=0; i<cart.size(); i++) {
			OrderDetails od = new OrderDetails();
			od.setOrderDetailsPK(new OrderDetailsPK(order, cart.get(i).getProduct()));
			
			od.setQuantity(cart.get(i).getQuantity());
			od.setEachPrice(cart.get(i).getProduct().getPrice());
			//orderDetailsRepository.save(od);
			odl.add(od);
		}
		
		orderRepository.save(order);
		orderDetailsRepository.saveAll(odl);
		session.removeAttribute("cart");
		session.removeAttribute("total");
		return "checkoutPage2";
	}

}
