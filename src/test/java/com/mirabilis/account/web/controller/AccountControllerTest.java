package com.mirabilis.account.web.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
import com.mirabilis.account.BaseTestCase;
import com.mirabilis.account.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest extends BaseTestCase {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;
	
	@Test
	public void getAllAccounts() throws Exception {
		given(accountService.getAllAccounts()).willReturn(accounts);

		mockMvc.perform(MockMvcRequestBuilders.get(BASE_END_POINT))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[0].lastName").value("Doe"))
				.andExpect(jsonPath("$[0].accountNumber").value("1234"));
	}
	
	@Test
	public void createAccount() throws Exception {	
		String requestBody = new ObjectMapper().writeValueAsString(accountToBeSaved);

		given(accountService.saveAccount(accountToBeSaved)).willReturn(accounts.get(3));

		mockMvc.perform(MockMvcRequestBuilders.post(BASE_END_POINT)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("message").value("account has been successfully added"));

	}
	
	@Test
	public void deleteAccount() throws Exception {
		given(accountService.getAccountById(1L)).willReturn(accounts.get(0));

		Mockito.doNothing().when(accountService).deleteAccount(accounts.get(0));

		mockMvc.perform(MockMvcRequestBuilders.delete(String.join("", Arrays.asList(BASE_END_POINT, "/1")))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("message").value("account successfully deleted"));

    }
	
	@Test
	public void badRequest() throws Exception {		
		given(accountService.getAccountById(5L)).willReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(String.join("", Arrays.asList(BASE_END_POINT, "/5")))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());

    }


}
