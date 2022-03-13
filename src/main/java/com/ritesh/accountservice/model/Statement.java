package com.ritesh.accountservice.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Statement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "account_id")
	private Long accountid;
	@Column(name = "datefield")
	private String dateField;
	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = true, insertable = false, updatable = false)
	Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Statement() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountid() {
		return accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public String getDateField() {
		if (!StringUtils.isEmpty(dateField)) {
			String[] s = dateField.split("\\.");
			if (s.length == 3)
				return s[2] + s[1] + s[0];
		}

		return dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public BigDecimal getAmount() {
		return amount.stripTrailingZeros();
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
