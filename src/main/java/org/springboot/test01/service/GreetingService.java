package org.springboot.test01.service;

import java.util.Collection;

import org.springboot.test01.model.Greeting;

public interface GreetingService
{

	Collection<Greeting> findAll();
	
	Greeting findOne(Long id);
	
	Greeting create(Greeting greeting);
	
	Greeting update(Greeting greeting);

	void delete(Long id);
	
	void evictCache();
	
	
}
