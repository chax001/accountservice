package com.ritesh.accountservice.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.model.Statement;

/**
 * @author rites
 *
 */
public interface UserService {

	public List<Account> getAllAccount() throws Exception;

	public List<Statement> getAllStatements() throws Exception;

	public List<Statement> getStatementsByID(Long accountID) throws Exception;

	public List<Statement> getStatementsBycriteria(Specification<Statement> spec) throws Exception;

}
