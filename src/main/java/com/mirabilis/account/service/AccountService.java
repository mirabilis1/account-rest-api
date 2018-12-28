package com.mirabilis.account.service;

import java.util.List;

import com.mirabilis.account.model.Account;

public interface AccountService {	
	
	List<Account> getAllAccounts();
	
	Account getAccountById(Long id);

	Account saveAccount(Account account);

	void deleteAccount(Account Account);
}
