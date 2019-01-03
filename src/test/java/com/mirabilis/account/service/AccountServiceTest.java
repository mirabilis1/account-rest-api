package com.mirabilis.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mirabilis.account.BaseTestCase;
import com.mirabilis.account.dao.AccountDao;
import com.mirabilis.account.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest extends BaseTestCase {
	@Mock
	private AccountDao accountDao;

	private AccountService accountService;
		

	@Before
	public void setUp() {
		accountService = new AccountServiceImpl(accountDao);
	}

	@Test
	public void getAllAccounts() {
		given(accountDao.findAll()).willReturn(accounts);

		List<Account> accountsFetched = accountService.getAllAccounts();
		assertThat(accountsFetched.size()).isEqualTo(4);
        assertThat(accountsFetched.containsAll(accounts)).isTrue();
	}
	
	@Test
	public void getAccountById() {		
		Optional<Account> optional = Optional.of(accounts.get(1));
		
		given(accountDao.findById(2L)).willReturn(optional);
		
		Account accountFetched = accountService.getAccountById(2L);
		assertThat(accountFetched.equals(accounts.get(1))).isTrue();		
	}	

	@Test
	public void saveAccount() {		
		given(accountDao.save(accountToBeSaved)).willReturn(accounts.get(3));

		Account accountFetched = accountService.saveAccount(accountToBeSaved);
		assertThat(accountFetched.equals(accounts.get(3))).isTrue();	
	}
	
	@Test
    public void deleteAccount(){ 
        Mockito.doNothing().when(accountDao).delete(accounts.get(0));
        
        accountService.deleteAccount(accounts.get(0));

        verify(accountDao, times(1)).delete(accounts.get(0));
    }
	
}
