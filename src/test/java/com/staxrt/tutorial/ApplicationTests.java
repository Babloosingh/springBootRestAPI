package com.staxrt.tutorial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.AssertJUnit;

import com.staxrt.tutorial.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;

	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users", HttpMethod.GET, entity,
				String.class);

		String responseBody = response.getBody().toString();
		System.out.println("Responce Body is for All list user " + responseBody);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		User user = restTemplate.getForObject(getRootUrl() + "/users/1", User.class);
		System.out.println(user.getFirstName());
		AssertJUnit.assertNotNull(user);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("Mukeshkumar@gmail.com");
		user.setFirstName("Mukesh");
		user.setLastName("Kumar");
		user.setCreatedBy("Mukesh_Admin");
		user.setUpdatedBy("Mukesh_Admin");

		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

		String responseBody = postResponse.getBody().toString();
		System.out.println("Responce Body for create New User " + responseBody);

		AssertJUnit.assertNotNull(postResponse);
		AssertJUnit.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 4;
		User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
		user.setFirstName("Dipak");
		user.setLastName("kumar");
		user.setEmail("dipakkumar@gmail.com");

		restTemplate.put(getRootUrl() + "/users/" + id, user);

		User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);

		System.out.println("Update for User  " + updatedUser.toString());

		AssertJUnit.assertNotNull(updatedUser);
	}

	@Test
	public void testDeletePost() {
		int id = 11;
		User user = restTemplate.getForObject(getRootUrl() + "/user/" + id, User.class);
		AssertJUnit.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/user/" + id);

		System.out.println(user.toString());
		
		// babloo

		System.out.println("Delete Users sucessfully ");

		try {
			user = restTemplate.getForObject(getRootUrl() + "/user/" + id, User.class);
		} catch (final HttpClientErrorException e) {
			AssertJUnit.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
