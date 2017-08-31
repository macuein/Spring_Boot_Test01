package org.springboot.test01;

import java.io.IOException;

import org.springboot.test01.web.api.GreetingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest
{
	
	protected MockMvc mvc;
	
	@Autowired WebApplicationContext webApplicationContext;
	
	protected void setUp()
	{
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected void setUp(GreetingController controller)
	{
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
}

