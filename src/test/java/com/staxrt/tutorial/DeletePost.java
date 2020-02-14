package com.staxrt.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.AssertJUnit;

import com.staxrt.tutorial.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletePost {
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
	public void testDeletePost() {
		int id = 9;
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
