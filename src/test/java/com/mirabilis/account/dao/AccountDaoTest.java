package com.mirabilis.account.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mirabilis.account.model.Account;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountDaoTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private AccountDao accountDao;
	
	@Test
    public void findall() {
		Account account = new Account(null, "Steven", "Doe", "1237");
				
        testEntityManager.persist(account);
        testEntityManager.flush();

		List<Account> accountsFetched = (List<Account>) accountDao.findAll();
        assertThat(accountsFetched.size()).isEqualTo(4);
        assertThat(accountsFetched.get(0).getFirstName()).isEqualTo("John");
        assertThat(accountsFetched.get(0).getLastName()).isEqualTo("Doe");
        assertThat(accountsFetched.get(0).getAccountNumber()).isEqualTo("1234");
        assertThat(accountsFetched.get(1).getFirstName()).isEqualTo("Jane");
        assertThat(accountsFetched.get(1).getLastName()).isEqualTo("Doe");
        assertThat(accountsFetched.get(1).getAccountNumber()).isEqualTo("1235");
        assertThat(accountsFetched.get(2).getFirstName()).isEqualTo("Jim");
        assertThat(accountsFetched.get(2).getLastName()).isEqualTo("Taylor");
        assertThat(accountsFetched.get(2).getAccountNumber()).isEqualTo("1236");
        assertThat(accountsFetched.get(3).getFirstName()).isEqualTo("Steven");
        assertThat(accountsFetched.get(3).getLastName()).isEqualTo("Doe");
        assertThat(accountsFetched.get(3).getAccountNumber()).isEqualTo("1237");
    }

}
