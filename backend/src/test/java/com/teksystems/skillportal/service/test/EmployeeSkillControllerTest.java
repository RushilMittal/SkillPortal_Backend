//package com.teksystems.skillportal.service.test;
//
//import java.util.Date;
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
//public class EmployeeSkillControllerTest {
//
//	@LocalServerPort
//	private int port;
//
//	TestRestTemplate restTemplate = new TestRestTemplate();
//
//	HttpHeaders headers = new HttpHeaders();
//
//	@Test
//	public void testGetById() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getById?id=5aa76b7a13ff77043025183e"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"1\",\"name\":\"Java\",\"skillId\":\"1\",\"ratedUsers\":3},\"rating\":4,\"lastModifiedDate\":1520921466643}";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testReadAll() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getEmployeeSkills?empId=101"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "[{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"1\",\"name\":\"Java\",\"skillId\":\"1\",\"ratedUsers\":3},\"rating\":3,\"lastModifiedDate\":1520939188659},"+
//		                  "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"2\",\"name\":\"C++\",\"skillId\":\"1\",\"ratedUsers\":3},\"rating\":5,\"lastModifiedDate\":1520939958949},"+
//				          "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"3\",\"name\":\"Perl\",\"skillId\":\"1\",\"ratedUsers\":2},\"rating\":1,\"lastModifiedDate\":1520939079520},"+
//		                  "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"4\",\"name\":\"AWS Device Farm\",\"skillId\":\"2\",\"ratedUsers\":3},\"rating\":3,\"lastModifiedDate\":1520940909621},"+
//				          "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"5\",\"name\":\"AWS Cloud\",\"skillId\":\"2\",\"ratedUsers\":2},\"rating\":4,\"lastModifiedDate\":1520938656732},"+
//		                  "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"6\",\"name\":\"HTML\",\"skillId\":\"3\",\"ratedUsers\":3},\"rating\":3,\"lastModifiedDate\":1520940870213},"+
//				          "{\"employeeId\":\"101\",\"subSkill\":{\"id\":\"7\",\"name\":\"Apache\",\"skillId\":\"3\",\"ratedUsers\":3},\"rating\":1,\"lastModifiedDate\":1520937535968}]";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testUpdate() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//		Date dt = new Date();
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/update?empId=103&subSkillId=4&rating=3"),
//				HttpMethod.POST, entity, String.class);
//		System.out.println(response.getBody());
//		long epoch = dt.getTime();
//		System.out.println(epoch);
//		String expected = "{\"employeeId\":\"103\",\"subSkill\":{\"id\":\"4\",\"name\":\"AWS Device Farm\",\"skillId\":\"2\",\"ratedUsers\":3},\"rating\":3,\"lastModifiedDate\":"+epoch+"}";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//
//	@Test
//	public void testGetBySkillIdCount() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getSkillCount?skillId=1"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "8";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//
//	@Test
//	public void testGetBySubSkillIdCount() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getSubSkillCount?subSkillId=1"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "3";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testGetBySubSkillIdExcept() {
//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getSubSkillsBySkillId?empId=101&skillId=3"),
//				HttpMethod.GET, entity, String.class);
//
//		String expected = "[{\"id\":\"8\",\"name\":\"Angular\",\"skillId\":\"3\",\"ratedUsers\":1},{\"id\":\"9\",\"name\":\"CSS\",\"skillId\":\"3\",\"ratedUsers\":2}]";
//		Assert.assertEquals(expected, response.getBody());
//	}
//
//	@Test
//	public void testGetEmployeeSkillPlaceholder(){
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/skill/getEmployeeSkillPlaceholder?empId=101"),
//				HttpMethod.GET, entity, String.class);
//
//
//	}
//
//
//	private String createURLWithPort(String uri) {
//		return "http://localhost:" + port + uri;
//	}
//
//}
