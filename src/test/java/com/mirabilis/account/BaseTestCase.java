package com.mirabilis.account;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;

import com.mirabilis.account.model.Account;

public abstract class BaseTestCase {
	
	protected final String BASE_END_POINT = "/api/account";

	protected static List<Account> accounts;
	
	protected static Account accountToBeSaved;

	@BeforeClass
	public static void setUpOneTime() throws Exception {
		accounts = Arrays.asList(new Account(1L, "John", "Doe", "1234"), new Account(2L, "Jane", "Doe", "1235"), new Account(3L, "Jim", "Taylor", "1236"), new Account(4L, "Steven", "Doe", "1237"));
		accountToBeSaved = new Account(null, "Steven", "Doe", "1237");
	}

}
