package org.springboot.test01.simplebatch;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("batch1")
@Component
public class GreetingBatchBean
{
	static final Logger logger = LogManager.getLogger(GreetingBatchBean.class.getName());

	@Autowired
	private GreetingService greetingService;
	
	@Scheduled(cron = "${batch.greeting.cron}")
	public void cronJob()
	{
		logger.debug("Starting Job ...");

		Collection<Greeting> greetings  = greetingService.findAll();
		logger.debug("There are {} items in the greetings", greetings.size());
		
		logger.debug("Ending Job ...");
	}
	
}
