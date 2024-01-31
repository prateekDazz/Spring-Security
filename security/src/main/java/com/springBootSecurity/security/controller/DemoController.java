package com.springBootSecurity.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
	
	@RequestMapping("/home")
	public String showHome()
	{
		System.out.println("inside home controller");
		return "home";
	}
	@GetMapping("/showMyLoginPage")
	
	public String loginPage()
	{
		return "login";
	}
	@RequestMapping("/manager")
	public String manager()
	{
		return "manager";
	}
	
	@RequestMapping("/lead")
	public String lead()
	{
		return "lead";
	}

}
