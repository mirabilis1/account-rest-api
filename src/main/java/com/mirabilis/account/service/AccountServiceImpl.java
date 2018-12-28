package com.mirabilis.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public List<Account> getAllAccounts() {
		return (List<Account>) accountDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Account getAccountById(Long id) {
		Optional<Account> optional = accountDao.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	@Transactional
	public Account saveAccount(Account account) {
		return accountDao.save(account);
	}

	@Override
	@Transactional
	public void deleteAccount(Account Account) {
		accountDao.delete(Account);
	}

}
