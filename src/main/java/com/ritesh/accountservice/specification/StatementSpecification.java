package com.ritesh.accountservice.specification;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.ritesh.accountservice.model.Statement;

/**
 * @author rites
 *
 */
public class StatementSpecification {

	private StatementSpecification() {

	}

	private static final Logger logger = LoggerFactory.getLogger(StatementSpecification.class);

	/**
	 * @param accountId
	 * @return
	 */
	public static Specification<Statement> getStatementByAccountId(Long accountId) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(statement.get("accountid"), accountId);
		};
	}

	/**
	 * @param startDate
	 * @return
	 */
	public static Specification<Statement> getStatementByStartDate(String startDate) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThanOrEqualTo(statement.get("dateField"), startDate);
		};
	}

	/**
	 * @param endDate
	 * @return
	 */
	public static Specification<Statement> getStatementByEndDate(String endDate) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThanOrEqualTo(statement.get("dateField"), endDate);
		};
	}

	/**
	 * @param startAmount
	 * @return
	 */
	public static Specification<Statement> getStatementByStartAmount(BigDecimal startAmount) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThanOrEqualTo(statement.get("amount"), startAmount);
		};
	}

	/**
	 * @param endAmount
	 * @return
	 */
	public static Specification<Statement> getStatementByEndAmount(BigDecimal endAmount) {

		return (statement, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThanOrEqualTo(statement.get("amount"), endAmount);
		};
	}

	/**
	 * @param accountid
	 * @param startAmount
	 * @param endAmount
	 * @return
	 */
	public static Specification<Statement> buildSpecBySearchCriteria(Long accountid, BigDecimal startAmount,
			BigDecimal endAmount) {
		boolean flag = false;
		Specification<Statement> spec = null;
		if (accountid != null) {
			logger.info("Creating query for accountid {}", accountid);
			flag = true;
			spec = Specification.where(getStatementByAccountId(accountid));
		}

		if (startAmount != null) {
			logger.info("Creating query for startAmount {}", startAmount);
			if (flag)
				spec = spec.and(getStatementByStartAmount(startAmount));
			else {
				flag = true;
				spec = Specification.where(getStatementByStartAmount(startAmount));
			}
		}
		if (endAmount != null) {
			logger.info("Creating query for endAmount {}", endAmount);
			if (flag)
				spec = spec.and(getStatementByEndAmount(endAmount));
			else
				spec = Specification.where(getStatementByEndAmount(endAmount));

		}
		return spec;
	}

}
