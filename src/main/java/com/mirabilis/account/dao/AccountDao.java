package com.mirabilis.account.dao;

import org.springframework.data.repository.CrudRepository;

import com.mirabilis.account.model.Account;

public interface AccountDao extends CrudRepository<Account, Long> {

}
