package org.springboot.test01.web.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.springboot.test01.Application;
import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.EmailService;
import org.springboot.test01.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController extends BaseController
{
	
	@Autowired
	private GreetingService greetingService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(
			value="/api/greetings",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings()
	{
		logger.debug("Rest getGreetings start ... ");
		Collection<Greeting> greetings = greetingService.findAll(); //greetingMap.values();
		logger.debug("Rest getGreetings end ... ");
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}

	@RequestMapping(
			value="/api/greetings/{id}",
			method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id)
	{
		logger.debug("Rest getGreeting start...");
		Greeting greeting = greetingService.findOne(id);
		logger.debug("Rest getGreeting end...");
		if(greeting == null)
		{
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/api/greetings",
			method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting( @RequestBody Greeting greeting)
	{
		logger.debug("Rest createGreetings start...");
		Greeting savedGreeting = greetingService.create(greeting);
		logger.debug("Rest createGreetings end...");		
		return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED); 
	}
	
	@RequestMapping(
			value="/api/greetings/{id}",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting( @RequestBody Greeting greeting)
	{
		logger.debug("Rest updateGreetings start...");
		Greeting updatedGreeting = greetingService.update(greeting);
		logger.debug("Rest updateGreetings end...");		
		if (updatedGreeting == null)
		{
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK); 
	}	
	
	@RequestMapping(
			value="/api/greetings/{id}",
			method=RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> deleteGreeting( @PathVariable("id") Long id, @RequestBody Greeting greeting)
	{
		logger.debug("Rest deleteGreetings start...");
		greetingService.delete(id);
		logger.debug("Rest deleteGreetings end...");		
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT); 
	}	
	
	@RequestMapping(
			value="/api/greetings/{id}/send",
			method=RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> sendGreeting( @PathVariable("id") Long id,
										@RequestParam(value="wait", defaultValue="false") boolean waitForAsyncResult)
	{
		logger.debug("sendGreeting start...");
		Greeting greeting = null;
		try
		{
			greeting = greetingService.findOne(id);
			if(greeting == null)
			{
				logger.debug("greeting not found");
				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
			}
			if(waitForAsyncResult)
			{
				Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
				boolean emailSent = asyncResponse.get();
				logger.debug("Email send status: {}", emailSent);
			}
			else
			{
				emailService.sendAsync(greeting);
			}
		}
		catch(Exception ex)
		{
			logger.debug("Error sending the greeting", ex);
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug("sendGreeting end...");	
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK); 
	}
	
	
	
}
