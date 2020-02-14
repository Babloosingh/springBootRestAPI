package com.staxrt.tutorial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.staxrt.tutorial.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CreateUser {

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
	public void testCreateUser() {
		// try {
		System.out.println("Create New User");
		User user = new User();
		user.setEmail("Uma@gmail.com");
		user.setFirstName("Uma");
		user.setLastName("Singh");
		user.setCreatedBy("Uma_Admin");
		user.setUpdatedBy("Uma_Admin");

		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

		String responseBody = postResponse.getBody().toString();
		System.out.println("Responce Body for create New User " + responseBody);

		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
