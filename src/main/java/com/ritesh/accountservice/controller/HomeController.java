package com.ritesh.accountservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rites
 *
 */
@Controller
public class HomeController {
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", "Account Service Application");
		return "home";
	}
}
