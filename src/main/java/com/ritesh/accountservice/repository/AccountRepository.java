package com.ritesh.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritesh.accountservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
