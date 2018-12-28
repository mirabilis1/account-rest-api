package com.mirabilis.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mirabilis.account.dao.AccountDao;
import com.mirabilis.account.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	@Mock
	private AccountDao accountDao;

	private AccountService accountService;

	@Before
	public void setUp() {
		accountService = new AccountServiceImpl(accountDao);
	}

	@Test
	public void getAllAccounts() {
		Account[] arr = { new Account(1L, "John", "Doe", "1234"), new Account(2L, "Jane", "Doe", "1235") };
		List<Account> accounts = Stream.of(arr).collect(Collectors.toList());

		given(accountDao.findAll()).willReturn(accounts);

		List<Account> accountsFetched = accountService.getAllAccounts();
		assertThat(accountsFetched.size()).isEqualTo(2);
		assertThat(accountsFetched.get(0).getFirstName()).isEqualTo("John");
		assertThat(accountsFetched.get(0).getLastName()).isEqualTo("Doe");
		assertThat(accountsFetched.get(0).getAccountNumber()).isEqualTo("1234");
	}
	
	@Test
	public void getAccountById() {
		Account account = new Account(1L, "John", "Doe", "1234");
		Optional<Account> optional = Optional.of(account);
		
		given(accountDao.findById(1L)).willReturn(optional);
		
		Account accountFetched = accountService.getAccountById(1L);
		assertThat(accountFetched.getFirstName()).isEqualTo("John");
		assertThat(accountFetched.getLastName()).isEqualTo("Doe");
		assertThat(accountFetched.getAccountNumber()).isEqualTo("1234");
	}	

	@Test
	public void saveAccount() {
		Account accountToBeSaved = new Account(null, "John", "Doe", "1234");
		Account accountSaved = new Account(1L, "John", "Doe", "1234");

		given(accountDao.save(accountToBeSaved)).willReturn(accountSaved);

		Account accountFetched = accountService.saveAccount(accountToBeSaved);
		assertThat(accountFetched.getId()).isEqualTo(1L);
		assertThat(accountFetched.getFirstName()).isEqualTo("John");
		assertThat(accountFetched.getLastName()).isEqualTo("Doe");
		assertThat(accountFetched.getAccountNumber()).isEqualTo("1234");
	}
	
	@Test
    public void deleteAccount(){
        Account accountToBeDeleted = new Account(1L, "John", "Doe", "1234");

        Mockito.doNothing().when(accountDao).delete(accountToBeDeleted);
        
        accountService.deleteAccount(accountToBeDeleted);

        verify(accountDao, times(1)).delete(accountToBeDeleted);
    }
	
}
