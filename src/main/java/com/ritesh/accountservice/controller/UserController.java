package com.ritesh.accountservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ritesh.accountservice.exceptionhandler.InvalidRequestException;
import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.model.Statement;
import com.ritesh.accountservice.service.UserService;
import com.ritesh.accountservice.specification.StatementSpecification;

/**
 * @author rites
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * @return it returns all accounts from DB
	 */
	@RequestMapping("/user/allaccountdetails")
	public List<Account> findAllEmployees() throws Exception {
		logger.info("Fetching All account details from DB");
		return userService.getAllAccount();

	}

	/**
	 * 
	 * this api is accessed by userrole only it doesn't accept any parameter and add
	 * last three months statements to model object
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/getstatementbyid")
	public String findAllStatement(Model model) throws Exception {
		logger.info("Fetching statement for user");
		List<Statement> list = getStatementByRange();
		model.addAttribute("statementList", list);
		return "home";
	}

	/**
	 * 
	 * this api is created for admin role and generates statememts based on search
	 * criteria
	 * 
	 * @param accountid
	 * @param startDate
	 * @param endDate
	 * @param startAmount
	 * @param endAmount
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/getstatementbyid")
	public String findStatement(@RequestParam(required = false) Long accountid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) BigDecimal startAmount,
			@RequestParam(required = false) BigDecimal endAmount, Model model) throws Exception {

		logger.info("fetching details for account id: " + accountid + " Startdate: " + startDate + "EndDate: " + endDate
				+ "From amount: " + startAmount + "To amount" + endAmount);

		validateInputParameter(accountid, startDate, endDate);

		List<Statement> statementList = null;
		Specification<Statement> spec = StatementSpecification.buildSpecBySearchCriteria(accountid, startAmount,
				endAmount);

		if (spec != null) {
			statementList = userService.getStatementsBycriteria(spec);
		} else {
			statementList = userService.getAllStatements();
		}
		logger.info("StatementList size is " + statementList.size());

		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			statementList = statementList.stream()
					.filter(s -> Integer.parseInt(s.getDateField()) >= Integer.parseInt(startDate)
							&& Integer.parseInt(s.getDateField()) <= Integer.parseInt(endDate))
					.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		} else if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {

			statementList = statementList.stream()
					.filter(s -> Integer.parseInt(s.getDateField()) >= Integer.parseInt(startDate))
					.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		} else if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {

			statementList = statementList.stream()
					.filter(s -> Integer.parseInt(s.getDateField()) <= Integer.parseInt(endDate))
					.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		} else if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate) && spec == null) {
			statementList = getStatementByRange();
		} else {
			statementList = statementList.stream().sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField()))
					.collect(Collectors.toList());
		}
		model.addAttribute("statementList", statementList);
		return "home";

	}

	private void validateInputParameter(Long accountid, String startDate, String endDate) {

		if (accountid != null && accountid < 0)
			throw new InvalidRequestException("Accountid is not valid " + accountid);

		DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
		try {
			if (!StringUtils.isEmpty(startDate))
				LocalDate.parse(startDate, dateFormatter);
			if (!StringUtils.isEmpty(endDate))
				LocalDate.parse(endDate, dateFormatter);
		} catch (Exception e) {
			throw new InvalidRequestException(
					"Request Input parameters are invalid. Please check format(YYYYMMDD) of startDate" + startDate
							+ " and endDate " + endDate,
					e);
		}
	}

	/**
	 * 
	 * this method filters last three months statements
	 * 
	 * @return list of statements
	 */
	private List<Statement> getStatementByRange() throws Exception {
		String startDate = LocalDate.now().minusMonths(3).format(DateTimeFormatter.BASIC_ISO_DATE);
		String endDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		logger.info("Creating statements for Last three months using from date: " + startDate + "To Date: " + endDate);
		List<Statement> list = userService.getAllStatements();
		list = list.stream()
				.filter(s -> Integer.parseInt(s.getDateField()) >= Integer.parseInt(startDate)
						&& Integer.parseInt(s.getDateField()) <= Integer.parseInt(endDate))
				.sorted((o1, o2) -> o1.getDateField().compareTo(o2.getDateField())).collect(Collectors.toList());
		logger.info("Statement size is " + list.size() + " for Last three months using from date: " + startDate
				+ "To Date: " + endDate);

		return list;
	}
}
