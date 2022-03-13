package com.ritesh.accountservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.model.Statement;
import com.ritesh.accountservice.service.UserService;
import com.ritesh.accountservice.specification.StatementSpecification;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/allaccountdetails")
	public List<Account> findAllEmployees() {
		logger.info("Fetching statement details from DB");
		return userService.getAllAccount();

	}

	@RequestMapping("/user/getstatementbyid")
	public String findAllStatement(Model model) {
		logger.info("Fetching statement for user");
		String startDate = LocalDate.now().minusMonths(3).format(DateTimeFormatter.BASIC_ISO_DATE);
		String endDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

		List<Statement> list = userService.getAllStatements();
		list = list.stream()
				.filter(s -> Integer.parseInt(s.getDateField()) >= Integer.parseInt(startDate)
						&& Integer.parseInt(s.getDateField()) <= Integer.parseInt(endDate))
				.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		model.addAttribute("statementList", list);
		return "home";
	}

	@RequestMapping("/admin/getstatementbyid")
	public String findStatement(@RequestParam(required = false) Long accountid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) BigDecimal startAmount,
			@RequestParam(required = false) BigDecimal endAmount, Model model) {

		List<Statement> statementList = null;

		statementList = userService.getStatementsBycriteria(
				StatementSpecification.buildSpecBySearchCriteria(accountid, startAmount, endAmount));

		statementList = statementList.stream()
				.filter(s -> Integer.parseInt(s.getDateField()) >= Integer.parseInt(startDate)
						&& Integer.parseInt(s.getDateField()) <= Integer.parseInt(endDate))
				.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		model.addAttribute("statementList", statementList);
		return "home";

	}
}
