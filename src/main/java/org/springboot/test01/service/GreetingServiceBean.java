 package org.springboot.test01.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springboot.test01.model.Greeting;
import org.springboot.test01.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
		propagation=Propagation.SUPPORTS, 
		readOnly=true)
public class GreetingServiceBean implements GreetingService
{

	@Autowired
	private GreetingRepository greetingRepository;
	
	@Autowired
	private CounterService counterService;
	
	@Override
	public Collection<Greeting> findAll()
	{
		counterService.increment("method.invoked.greetingServiceBean.findAll");
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(
			value="greetings",
			key="#id")
	public Greeting findOne(Long id)
	{
		counterService.increment("method.invoked.greetingServiceBean.findOne");
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CachePut(
			value="greetings",
			key="#result.id")
 	public Greeting create(Greeting greeting)
	{
		counterService.increment("method.invoked.greetingServiceBean.create");
		if (greeting.getId() != null)
		{
			//Cannot create greeting with specified id.
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		
//		if (savedGreeting.getId() == 4L)
//		{
//			throw new RuntimeException("Roll me back!!!");
//		}
		
		return savedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)	
	@CachePut(
			value="greetings",
			key="#greeting.id")
	public Greeting update(Greeting greeting)
	{
		counterService.increment("method.invoked.greetingServiceBean.update");
		Greeting greetingPersisted = greetingRepository.findOne(greeting.getId());
		if(greetingPersisted == null)
		{
			//cannot update a greeting that is not persisted
			return null;
		}
		
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false) 	
	@CacheEvict(
			value="greetings",
			key="#id")	
	public void delete(Long id)
	{
		counterService.increment("method.invoked.greetingServiceBean.delete");
		greetingRepository.delete(id);
	}
	
	@Override
	@CacheEvict(
			value="greetings",
			allEntries=true)		
	public void evictCache()
	{
		counterService.increment("method.invoked.greetingServiceBean.evictcache");
	}

}
