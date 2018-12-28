package com.mirabilis.account.web.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirabilis.account.model.Account;
import com.mirabilis.account.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;
	
	@Test
	public void getAllAccounts() throws Exception {
		Account[] arr = { new Account(1L, "John", "Doe", "1234"), new Account(2L, "Jane", "Doe", "1235") };
		List<Account> accounts = Stream.of(arr).collect(Collectors.toList());

		given(accountService.getAllAccounts()).willReturn(accounts);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/account"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[0].lastName").value("Doe"))
				.andExpect(jsonPath("$[0].accountNumber").value("1234"));
	}
	
	@Test
	public void createAccount() throws Exception {
		Account accountToBeSaved = new Account(null, "John", "Doe", "1234");
		Account accountSaved = new Account(1L, "John", "Doe", "1234");

		String requestBody = new ObjectMapper().writeValueAsString(accountToBeSaved);

		given(accountService.saveAccount(accountToBeSaved)).willReturn(accountSaved);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/account")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("message").value("account has been successfully added"));

	}
	
	@Test
	public void deleteAccount() throws Exception {
		Account account = new Account(1L, "John", "Doe", "1234");

		given(accountService.getAccountById(1L)).willReturn(account);

		Mockito.doNothing().when(accountService).deleteAccount(account);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("message").value("account successfully deleted"));

    }
	
	@Test
	public void badRequest() throws Exception {		
		given(accountService.getAccountById(5L)).willReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/5")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());

    }


}
