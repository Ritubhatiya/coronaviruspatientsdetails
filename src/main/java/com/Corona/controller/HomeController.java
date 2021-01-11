package com.Corona.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.Corona.dao.HospitalRepository;
import com.Corona.entities.Hospital;
import com.smart.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	@Autowired 
	private HospitalRepository hospitalRepository;
	
	 
	 
	 
	  
	 
	/*
	 * @GetMapping("/test")
	 * 
	 * @ResponseBody public String test() {
	 * 
	 * Hospital hospital=new Hospital(); hospital.setName("lovesh hospital");
	 * hospital.setEmail("lovesh@123");
	 * 
	 * hospitalRepository.save(hospital);
	 * 
	 * return"working"; }
	 */
	 
	
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "home- corona patients details");
		
		return "home";
		
		
		
	}
	
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "about- corona patients details");
		
		return "about";
		
		
		
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title", "Register- corona patients details");
		model.addAttribute("hospital", new Hospital());
		
		return "signup";
		
		
		
	}
	
	//handel for registering hospital 
	
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerHospital(@Valid @ModelAttribute("hospital") Hospital hospital,BindingResult result1,@RequestParam(value="agreement",defaultValue="true")boolean agreement,Model model,HttpSession session)
	{
		
		
		try {
			
		
		if(!agreement) {
			
			System.out.println("You have not agreed the terms and conditions");
			throw new Exception("You have not agreed the terms and conditions");
			}
		
		
		if(result1.hasErrors()) {
			System.out.println("ERROE "+ result1.toString());
			model.addAttribute("hospital", hospital);
			return "signup";
			
		}
		
		hospital.setRole("ROLE_HOSPITAL");
		hospital.setEnabled(true);
		hospital.setImageurl("default.png");
		hospital.setPassword(passwordEncoder.encode(hospital.getPassword()));
		
		System.out.println("Agreement " + agreement);
		System.out.println("HOSPITAL " + hospital);
		
		 Hospital result=this.hospitalRepository.save(hospital);
		
		model.addAttribute("hospital",new Hospital());
		session.setAttribute("message", new Message("Successfully Registerd !!", "alert-success"));
		return "signup";
		
		
		}
		catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("hospital", hospital);
			session.setAttribute("message", new Message("Somthing went wrong!!"+ e.getMessage(),"alert-danger"));
			return "signup";
			
		}
	
	
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
