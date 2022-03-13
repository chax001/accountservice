package com.ritesh.accountservice.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.ritesh.accountservice.model.Statement;

public class StatementSpecification {

	public static Specification<Statement> getStatementByAccountId(Long accountId) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(statement.get("accountid"), accountId);
		};
	}

	public static Specification<Statement> getStatementByStartDate(String startDate) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThanOrEqualTo(statement.get("dateField"), startDate);
		};
	}

	public static Specification<Statement> getStatementByEndDate(String endDate) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThanOrEqualTo(statement.get("dateField"), endDate);
		};
	}

	public static Specification<Statement> getStatementByStartAmount(BigDecimal startAmount) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThanOrEqualTo(statement.get("amount"), startAmount);
		};
	}

	public static Specification<Statement> getStatementByEndAmount(BigDecimal endAmount) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThanOrEqualTo(statement.get("amount"), endAmount);
		};
	}

	public static Specification<Statement> buildSpecBySearchCriteria(Long accountid, BigDecimal startAmount,
			BigDecimal endAmount) {

		Specification<Statement> spec = null;
		if (accountid != null)
			spec = Specification.where(getStatementByAccountId(accountid));

		if (startAmount != null && endAmount != null) {
			spec = spec.and(getStatementByStartAmount(startAmount)).and(getStatementByEndAmount(endAmount));
		}

		return spec;
	}

}
