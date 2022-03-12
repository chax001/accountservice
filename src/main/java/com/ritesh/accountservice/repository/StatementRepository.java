package com.ritesh.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ritesh.accountservice.model.Statement;

public interface StatementRepository extends JpaRepository<Statement, Integer>, JpaSpecificationExecutor<Statement> {

}
