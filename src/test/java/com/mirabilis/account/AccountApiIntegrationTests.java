package com.mirabilis.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
public class AccountApiIntegrationTests extends BaseTestCase {

	@Autowired
	private TestRestTemplate testRestTemplate;	
		

	/**
	 * Integration test for RESTful GET Endpoint to get all accounts
	 */
	@Test
	public void firstTest() {
		ParameterizedTypeReference<List<Account>> responseType = new ParameterizedTypeReference<List<Account>>() {};
		ResponseEntity<List<Account>> response = testRestTemplate.exchange(BASE_END_POINT, HttpMethod.GET, null, responseType);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<Account> intersection = accounts.stream()
				.filter(response.getBody()::contains)
				.collect(Collectors.toList());
		// response contains first three items of the List accounts
		assertThat(intersection.size() == 3).isTrue();	
		assertThat(intersection.contains(accounts.get(3))).isFalse();
	}

	/**
	 * Integration test for RESTful POST Endpoint to create a new account
	 */
	@Test
	public void secondTest() {		
		String responseJson = testRestTemplate.postForObject(BASE_END_POINT, accountToBeSaved , String.class);
		assertThat(responseJson).isEqualTo("{\"message\":\"account has been successfully added\"}");
		
		ParameterizedTypeReference<List<Account>> responseType = new ParameterizedTypeReference<List<Account>>() {};
		ResponseEntity<List<Account>> response = testRestTemplate.exchange(BASE_END_POINT, HttpMethod.GET, null, responseType);		
		assertThat(response.getBody().size()).isEqualTo(4);
        assertThat(response.getBody().containsAll(accounts)).isTrue();		
	}
	
	/**
	 * Integration test for RESTful DELETE Endpoint to delete an existing account
	 */
	@Test
	public void thirdTest() {		
		ResponseEntity<String> responseJson = testRestTemplate.exchange(String.join("", Arrays.asList(BASE_END_POINT, "/1")), HttpMethod.DELETE, null, String.class);

		assertThat(responseJson.getBody()).isEqualTo("{\"message\":\"account successfully deleted\"}");
	}
	
}
