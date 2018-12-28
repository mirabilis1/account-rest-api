package com.mirabilis.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirabilis.account.dao.AccountDao;
import com.mirabilis.account.model.Account;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	@Autowired
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account saveAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccount(Account Account) {
		// TODO Auto-generated method stub
		
	}

}
