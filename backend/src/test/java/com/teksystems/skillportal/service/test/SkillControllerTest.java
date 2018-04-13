//package com.teksystems.skillportal.service.test;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.Arrays;
//
//import com.teksystems.skillportal.SkillPortal_Skill;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.boot.context.embedded.LocalServerPort;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.teksystems.skillportal.SkillPortal_Skill;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SkillPortal_Skill.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class SkillControllerTest {
//
//	@LocalServerPort
//	private int port;
//
//	TestRestTemplate restTemplate = new TestRestTemplate();
//
//	HttpHeaders headers = new HttpHeaders();
//
//	@Test
//	public void testGetBySkillId() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getBySkillId?skillId=1"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "{\"id\":\"1\",\"name\":\"Programming\",\"ratedUsers\":8}";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testGetAllSkills() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/all"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "[{"+
//		        "\"id\":\"1\"," +
//				"\"name\":\"Programming\"," +
//				"\"ratedUsers\":8" +
//				"}," +
//				"{" +
//				"\"id\":\"2\"," +
//				"\"name\":\"AWS\"," +
//				"\"ratedUsers\":5" +
//				"}," +
//				"{" +
//				"\"id\":\"3\"," +
//				"\"name\":\"Front End\"," +
//				"\"ratedUsers\":9" +
//				"}]";
//        System.out.println(response.getBody());
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testGetBySkillName() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getBySkillName?skillName=AWS"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "{\"id\":\"2\",\"name\":\"AWS\",\"ratedUsers\":5}";
//        System.out.println(response.getBody());
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	private String createURLWithPort(String uri) {
//		return "http://localhost:" + port + uri;
//	}
//}
//
