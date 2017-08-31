package org.springboot.test01.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collection;

import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

public class GreetingItemReader implements ItemReader<Greeting> 
{
	static final Logger logger = LogManager.getLogger(GreetingItemReader.class.getName());

	@Autowired
	private GreetingService greetingService;
	private Collection<Greeting> greetings;
	
	public GreetingItemReader()
	{
		greetings = greetingService.findAll();
		if (greetings!= null)
			logger.debug(String.format("*********** Greeting Size: %d ", greetings.size()));
		else
			logger.debug(String.format("*********** Greeting Size: null "));	
	}

	public Greeting read() throws Exception,
							UnexpectedInputException,
							ParseException 
	{
		
		
		if (greetings != null || greetings.iterator().hasNext()) 
		{
			Greeting readGreeting = greetings.iterator().next();
			logger.debug(String.format("*********** Greeting Id: %d ; Greeting Text: %s", readGreeting.getId(), readGreeting.getText()));
			return readGreeting;
		}
		else
		{
			logger.debug(String.format("*********** Greeting Id: 0 ; Greeting Text: No greeting"));
			return null;
		}		

	}
	

}
