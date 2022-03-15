package com.ritesh.accountservice;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ritesh.accountservice.model.Account;
import com.ritesh.accountservice.repository.AccountRepository;
import com.ritesh.accountservice.repository.StatementRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountserviceApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TestRestTemplate template;

	private MockMvc mockMvc;
	@MockBean
	private AccountRepository accountRepository;
	@MockBean
	private StatementRepository statementRepository;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		List<Account> accountList = new ArrayList<>();
		Account acc = new Account();
		acc.setAccountId(1l);
		accountList.add(acc);
		Mockito.when(accountRepository.findAll()).thenReturn(accountList);
	}

	@WithMockUser("/user")
	@Test
	void whenGetHomeOK() throws Exception {
		MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@WithMockUser("/user")
	@Test
	void whenGetAllStatementthenOK() throws Exception {

		MvcResult result = mockMvc.perform(get("/user/allaccountdetails").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());

	}

	@WithMockUser("/user")
	@Test
	void whenFindAllstatementbyUserOK() throws Exception {
		MvcResult result = mockMvc.perform(get("/user/getstatementbyid").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void whenFindAllAdminStatementthenOK() {
		ResponseEntity<?> result = template.withBasicAuth("admin", "admin").getForEntity("/admin/getstatementbyid",
				String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
