package com.ritesh.accountservice.model;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author rites
 *
 */
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long accountId;
	@Column(name = "account_type")
	private String accountType;
	@Column(name = "account_number")

	private String accountNumber;

//	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
//	List<Statement> list;

//	public List<Statement> getList() {
//		return list;
//	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

//	public void setList(List<Statement> list) {
//		this.list = list;
//	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return Base64.getEncoder().encodeToString(accountNumber.getBytes());
		// return accountNumber.replaceAll("\\d", "*");
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
