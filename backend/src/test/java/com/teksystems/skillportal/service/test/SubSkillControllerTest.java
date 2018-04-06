//package com.teksystems.skillportal.service.test;
//
//import com.teksystems.skillportal.SkillPortal_Skill;
//import com.teksystems.skillportal.model.Skill;
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
//
//public class SubSkillControllerTest {
//
//	@LocalServerPort
//	private int port;
//
//	TestRestTemplate restTemplate = new TestRestTemplate();
//
//	HttpHeaders headers = new HttpHeaders();
//
//	@Test
//	public void testGetBySubSkillId() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getBySubSkillId?subSkillId=1"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "{\"id\":\"1\",\"name\":\"Java\",\"skillId\":\"1\",\"ratedUsers\":0}";
//		System.out.println(response.getBody());
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	private String createURLWithPort(String uri) {
//		return "http://localhost:" + port + uri;
//	}
//
//
//}
