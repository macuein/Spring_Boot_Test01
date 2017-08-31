package org.springboot.test01.web.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springboot.test01.AbstractControllerTest;
import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingControllerTest extends AbstractControllerTest
{
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	@Before
	public void setUp()
	{
		super.setUp();
		greetingService.evictCache();
	}
	
	@Test
	public void testGetGreetings() throws Exception
	{
		String uri = "/api/greetings";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
									.accept(MediaType.APPLICATION_JSON)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("FAIL - Expected http responde 200",200,status);
		Assert.assertTrue("FAIL - Expected htts response content", content.trim().length() > 0);
	}
	
	@Test
	public void testGetGreeting() throws Exception
	{
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
									.accept(MediaType.APPLICATION_JSON)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("FAIL - Expected http responde 200", 200, status);
		Assert.assertTrue("FAIL - Expected htts response content", content.trim().length() > 0);
	}	
	
	@Test
	public void testGetGreetingNotFound() throws Exception
	{
		String uri = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
									.accept(MediaType.APPLICATION_JSON)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("FAIL - Expected http responde 404", 404, status);
		Assert.assertTrue("FAIL - Expected htts response body to be empty", content.trim().length() == 0);
	}		
	
	@Test
	public void testCreateGreeting() throws Exception
	{
		String uri = "/api/greetings";
		String textTest = "Test Text";
		Greeting greeting = new Greeting();
		greeting.setText(textTest);
		String inputJson = super.mapToJson(greeting);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(inputJson)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		logger.debug("responseid={}, response.content={}", status, content);
		
		Assert.assertEquals("FAIL - Expected http responde 201",201,status);
		Assert.assertTrue("FAIL - Expected http response content", content.trim().length() > 0);
		
		Greeting createdGreeting = super.mapFromJson(content, Greeting.class);
		
		Assert.assertNotNull("FAIL - expected greeting not null",createdGreeting);
		Assert.assertNotNull("FAIL - expected greeting.id not null",createdGreeting.getId());
		Assert.assertEquals("FAIL - expected greeting.text match",textTest,createdGreeting.getText());		
	}	

	@Test
	public void testUpdateGreeting() throws Exception
	{
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		Greeting greeting = greetingService.findOne(id);
		String updTextTest = greeting.getText() + "Test Test";
		greeting.setText(updTextTest);
		String inputJson = super.mapToJson(greeting);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id)
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(inputJson)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		logger.debug("responseid={}, response.content={}", status, content);
		
		Assert.assertEquals("FAIL - Expected http responde 200",200,status);
		Assert.assertTrue("FAIL - Expected http response content", content.trim().length() > 0);
		
		Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);
		
		Assert.assertNotNull("FAIL - expected greeting not null",updatedGreeting);
		Assert.assertEquals("FAIL - expected updated greeting.id not changed",greeting.getId(),updatedGreeting.getId());
		Assert.assertEquals("FAIL - expected updated greeting.text match",updTextTest, updatedGreeting.getText());		
	}		
	
	@Test
	public void testDeleteGreeting() throws Exception
	{
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		Greeting greeting = new Greeting();
		greeting.setId(id);
		String inputJson = super.mapToJson(greeting);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
									.contentType(MediaType.APPLICATION_JSON)
									.content(inputJson)
								).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		logger.debug("responseid={}, response.content={}", status, content);
		
		Assert.assertEquals("FAIL - Expected http responde 204",204,status);
		Assert.assertTrue("FAIL - Expected http response content to be empty", content.trim().length() == 0);
		
		Greeting deletedGreeting = greetingService.findOne(id);
		
		Assert.assertNull("FAIL - expected greeting to be null",deletedGreeting);
	}			
	
	
}
