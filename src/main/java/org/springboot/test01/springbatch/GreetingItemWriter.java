package org.springboot.test01.springbatch;

import java.util.List;

import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class GreetingItemWriter implements ItemWriter<Greeting>
{
	
	@Autowired
	private GreetingService greetingService;

	public void write(List<? extends Greeting> items) throws Exception 
	{
		for (final Greeting greeting : items) 
		{
			Greeting greetingRegistered = greetingService.create(greeting);
		}
	}
}
