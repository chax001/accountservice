package com.ritesh.accountservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.service.UserService;

@RestController
@RequestMapping("/account")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/alldetails")
	public List<Account> findAllEmployees() {

		return userService.getAllAccount();

	}
}
