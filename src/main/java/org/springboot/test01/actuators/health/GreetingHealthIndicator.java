package org.springboot.test01.actuators.health;

import java.util.Collection;

import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class GreetingHealthIndicator implements HealthIndicator
{

	@Autowired
	private GreetingService greetingService;
	
	@Override
	public Health health()
	{

		Collection<Greeting> greetings = greetingService.findAll();
		if (greetings == null || greetings.size() == 0)
		{
			return Health.down().withDetail("greeting.count", 0).build();
		}
		return Health.up().withDetail("greeting.count", greetings.size()).build();
	}

}
