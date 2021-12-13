package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Member;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.FileUtils;

@Controller
@RequestMapping("staff")
public class StaffController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@RequestMapping("/")
	public String staffLogin() {
		return "staffLogin";
	}
	
	@PostMapping("staffCenter")
	@ResponseBody
	public Map<String, String> staffCenter(String username, String password) {
		Map<String, String> map = new HashMap<>();
		//System.out.println(username + ":" + password);
		//未連接資料庫
		if(username.equals("admin") && password.equals("admin")) {
			map.put("result", "success");
		}else {
			map.put("result", "failed");
		}
		
		return map;
	}
	
	@GetMapping("staffCenter")
	public String staffCenterPage() {
		return "staffCenter";
	}
	
	@GetMapping("addProduct")
	public String addProduct() {
		return "staffAddProduct";
	}
	
	@PostMapping("productUpload")
	@ResponseBody
	public Map<String, String> productUpload(@RequestParam("_image") MultipartFile _image, Product product){
		Map<String, String> map = new HashMap<>();
		
		System.out.println(product.toString() + "\n圖檔路徑:" + product.getImage());
		
		if(_image == null) {
			System.out.println("圖檔為null");
		}else if(_image.isEmpty()){
			System.out.println("圖檔為空");
		}

		// 要上傳的目標檔案存放路徑
		String localPath = "C:\\Users\\Lin\\Documents\\eclipse-workspace\\ShoppingDemo1\\src\\main\\resources\\static\\images\\products\\";
		// 上傳成功或者失敗的提示
		String msg = "";
		if (FileUtils.upload(_image, localPath, _image.getOriginalFilename())){// 文件名
		// 上傳成功，給出頁面提示
		msg = "上傳成功！";
		}else {
		msg = "上傳失敗！";
		}
		// 顯示圖片
		map.put("msg", msg);
		map.put("fileName", "/images/products/"+_image.getOriginalFilename());

		//新增至資料庫
		long count = productRepository.countByType(product.getType());
		product.setId(product.getType() + "_" + (count+1));
		product.setImage("/images/products/"+_image.getOriginalFilename());
		productRepository.save(product);
		map.put("result", "success");
		
		return map;
	}
	/*
	//測試用
	@RequestMapping("fileUpload")
	public String upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){
	// 要上傳的目標檔案存放路徑
	String localPath = "E:/";
	// 上傳成功或者失敗的提示
	String msg = "";
	if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
	// 上傳成功，給出頁面提示
	msg = "上傳成功！";
	}else {
	msg = "上傳失敗！";
	}
	// 顯示圖片
	map.put("msg", msg);
	map.put("fileName", file.getOriginalFilename());
	return "redirect:/staff/staffCenter";
	}
	*/
	
	@GetMapping("memberManage")
	public String memberManage() {
		return "staffMemberManage";
	}
	
	@PostMapping("searchMember")
	@ResponseBody
	public List<Member> searchMember(String name){
		List<Member> list = new ArrayList<Member>();
		list = memberRepository.findByNameLike("%"+name+"%");
		return list;
	}
	
	@PostMapping("searchMemberOrder/{id}")
	@ResponseBody
	public List<Order> searchMemberOrder(@PathVariable int id) {
		
		List<Order> list = new ArrayList<Order>();
		
		//System.out.println(orderRepository.findByMemberIdId(((Member)session.getAttribute("user")).getId()));
		list = orderRepository.findByMemberIdId(id);
		//list = orderRepository.findByMemberId((Member)session.getAttribute("user"));
		if(list.size()==0) {
			System.out.println("無購物");
		}else {
			System.out.println("有購物");
		}
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).toString());
		}

		return list;
		
	}
}
