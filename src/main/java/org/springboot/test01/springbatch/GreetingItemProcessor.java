package org.springboot.test01.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springboot.test01.model.Greeting;
import org.springframework.batch.item.ItemProcessor;

public class GreetingItemProcessor implements ItemProcessor<Greeting, Greeting> 
{
	static final Logger logger = LogManager.getLogger(GreetingItemProcessor.class.getName());

	@Override
	public Greeting process(final Greeting greeting) throws Exception 
	{
		
		final String saludo = greeting.getText().toUpperCase();

		final Greeting transformedGreeting = new Greeting();
		transformedGreeting.setText(saludo);
		
		logger.debug("Converting (" + greeting.getText() + ") into (" + transformedGreeting.getText() + ")");
		
		return transformedGreeting;
	}
	
}
