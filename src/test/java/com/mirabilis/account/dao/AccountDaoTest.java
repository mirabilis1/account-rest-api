package com.mirabilis.account.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mirabilis.account.BaseTestCase;
import com.mirabilis.account.model.Account;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountDaoTest extends BaseTestCase {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private AccountDao accountDao;
		
	
	@Test
    public void findall() {				
        testEntityManager.persist(accountToBeSaved);
        testEntityManager.flush();

		List<Account> accountsFetched = (List<Account>) accountDao.findAll();
        assertThat(accountsFetched.size()).isEqualTo(4);
        assertThat(accountsFetched.containsAll(accounts)).isTrue();        
    }
	
}
