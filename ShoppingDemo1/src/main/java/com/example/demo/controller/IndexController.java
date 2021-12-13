package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("shopping")
public class IndexController {

	@RequestMapping("/")
	public String homePage() {
		
        return "index";
	}
    
}
