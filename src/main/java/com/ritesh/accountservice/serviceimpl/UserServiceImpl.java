package com.ritesh.accountservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.model.Statement;
import com.ritesh.accountservice.repository.AccountRepository;
import com.ritesh.accountservice.repository.StatementRepository;
import com.ritesh.accountservice.service.UserService;
import com.ritesh.accountservice.specification.StatementSpecification;

/**
 * @author rites
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	StatementRepository statementRepository;

	/**
	 *
	 */
	@Override
	public List<Account> getAllAccount() throws Exception {

		return accountRepository.findAll();
	}

	/**
	 *
	 */
	@Override
	public List<Statement> getAllStatements() throws Exception {

		return statementRepository.findAll();
	}

	/**
	 *
	 */
	@Override
	public List<Statement> getStatementsByID(Long accountId) throws Exception {

		return statementRepository.findAll(StatementSpecification.getStatementByAccountId(accountId));
	}

	/**
	 *
	 */
	@Override
	public List<Statement> getStatementsBycriteria(Specification<Statement> spec) throws Exception {

		return statementRepository.findAll(spec);
	}

}
