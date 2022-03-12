package com.ritesh.accountservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.repository.AccountRepository;
import com.ritesh.accountservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> getAllAccount() {

		return accountRepository.findAll();
	}

}
