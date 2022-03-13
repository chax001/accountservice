package com.ritesh.accountservice.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.model.Statement;

public interface UserService {

	public List<Account> getAllAccount();

	public List<Statement> getAllStatements();

	public List<Statement> getStatementsByID(Long accountID);

	public List<Statement> getStatementsBycriteria(Specification<Statement> spec);

}
