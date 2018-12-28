package com.mirabilis.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mirabilis.account.model.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountApiIntegrationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * Integration test for RESTful GET Endpoint to get all accounts
	 */
	@Test
	public void firstTest() {
		ParameterizedTypeReference<List<Account>> responseType = new ParameterizedTypeReference<List<Account>>() {};
		ResponseEntity<List<Account>> response = testRestTemplate.exchange("/api/account", HttpMethod.GET, null, responseType);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);		
		assertThat(response.getBody().get(0).getId()).isEqualTo(1);
		assertThat(response.getBody().get(0).getFirstName()).isEqualTo("John");
		assertThat(response.getBody().get(0).getLastName()).isEqualTo("Doe");
		assertThat(response.getBody().get(0).getAccountNumber()).isEqualTo("1234");
		assertThat(response.getBody().get(1).getId()).isEqualTo(2);
		assertThat(response.getBody().get(1).getFirstName()).isEqualTo("Jane");
		assertThat(response.getBody().get(1).getLastName()).isEqualTo("Doe");
		assertThat(response.getBody().get(1).getAccountNumber()).isEqualTo("1235");
		assertThat(response.getBody().get(2).getId()).isEqualTo(3);
		assertThat(response.getBody().get(2).getFirstName()).isEqualTo("Jim");
		assertThat(response.getBody().get(2).getLastName()).isEqualTo("Taylor");
		assertThat(response.getBody().get(2).getAccountNumber()).isEqualTo("1236");
	}

	/**
	 * Integration test for RESTful POST Endpoint to create a new account
	 */
	@Test
	public void secondTest() {
		Account account = new Account(null, "Steven", "Doe", "1237");
		String responseJson = testRestTemplate.postForObject("/api/account", account , String.class);
		assertThat(responseJson).isEqualTo("{\"message\":\"account has been successfully added\"}");
		
		ParameterizedTypeReference<List<Account>> responseType = new ParameterizedTypeReference<List<Account>>() {};
		ResponseEntity<List<Account>> response = testRestTemplate.exchange("/api/account", HttpMethod.GET, null, responseType);		
		assertThat(response.getBody().get(3).getId()).isEqualTo(4);
		assertThat(response.getBody().get(3).getFirstName()).isEqualTo("Steven");
		assertThat(response.getBody().get(3).getLastName()).isEqualTo("Doe");
		assertThat(response.getBody().get(3).getAccountNumber()).isEqualTo("1237");
	}
	
	/**
	 * Integration test for RESTful DELETE Endpoint to delete an existing account
	 */
	@Test
	public void thirdTest() {
		ResponseEntity<String> responseJson = testRestTemplate.exchange("/api/account/1", HttpMethod.DELETE, null, String.class);

		assertThat(responseJson.getBody()).isEqualTo("{\"message\":\"account successfully deleted\"}");
	}
	
}
