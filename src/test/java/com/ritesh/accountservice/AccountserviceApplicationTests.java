package com.ritesh.accountservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountserviceApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@Test
	void whenGetAllHomeOK() {
		ResponseEntity<?> result = template.withBasicAuth("user", "user").getForEntity("/", String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	void whenGetAllStatementthenOK() {
		ResponseEntity<?> result = template.withBasicAuth("user", "user").getForEntity("/user/allaccountdetails",
				String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	void whenFindAllUserStatementthenOK() {
		ResponseEntity<?> result = template.withBasicAuth("user", "user").getForEntity("/user/getstatementbyid",
				String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void whenFindAllAdminStatementthenOK() {
		ResponseEntity<?> result = template.withBasicAuth("admin", "admin").getForEntity("/admin/getstatementbyid",
				String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
