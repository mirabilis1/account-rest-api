package com.mirabilis.account.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mirabilis.account.model.Account;
import com.mirabilis.account.service.AccountService;
import com.mirabilis.account.web.ApiMessage;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Account>> getAccounts() {
		List<Account> list = accountService.getAllAccounts();
		return new ResponseEntity<List<Account>>(list, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ApiMessage> createAccount(@RequestBody Account account) {
		accountService.saveAccount(account);
		return new ResponseEntity<ApiMessage>(new ApiMessage("account has been successfully added"), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
		Account account = accountService.getAccountById(id);
		if (account == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		accountService.deleteAccount(account);
		return new ResponseEntity<ApiMessage>(new ApiMessage("account successfully deleted"), HttpStatus.OK);
	}

}
