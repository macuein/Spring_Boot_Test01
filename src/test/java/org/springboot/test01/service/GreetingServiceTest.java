package org.springboot.test01.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springboot.test01.AbstractTest;
import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingServiceTest extends AbstractTest
{

	@Autowired
	private GreetingService service;
	
	@Before
	public void setUp()
	{
		service.evictCache();
	}
	
	@After
	public void tearDown()
	{
		//clean up after earch test method
	}

	@Test
	public void testFindAll()
	{
		Collection<Greeting> list = service.findAll();
		Assert.assertNotNull("FAIL - expected not null", list);
		//Assert.assertEquals("FAIL - expected size", 6, list.size());
		Assert.assertEquals("FAIL - expected size", 3, list.size());
	}

	@Test
	public void testFindOne()
	{
		Long id = new Long(1);
		Greeting entity = service.findOne(id);
		Assert.assertNotNull("FAIL - expected not null", entity);
		Assert.assertEquals("FAIL - expected id attribute match", id, entity.getId());
	}

	@Test
	public void testFindOneNotFound()
	{
		Long id = Long.MAX_VALUE;
		Greeting entity = service.findOne(id);
		Assert.assertNull("FAIL - expected null", entity);
	}	

	@Test
	public void testCreate()
	{
		Greeting entity = new Greeting();
		entity.setText("Test text");
		Greeting createdEntity = service.create(entity);
		Assert.assertNotNull("FAIL - expected not null", createdEntity);
		Assert.assertNotNull("FAIL - expected attribute id not null", createdEntity.getId());
		Assert.assertEquals("FAIL - expected text attribute match", entity.getText(), createdEntity.getText());
		Collection<Greeting> list = service.findAll();
		//Assert.assertEquals("FAIL - expected size", 7, list.size());
		Assert.assertEquals("FAIL - expected size", 4, list.size());
	}
	
	@Test
	public void testCreateWithId()
	{
		Exception e = null;
		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setText("Test text");
		try
		{
			logger.debug("Trying creation with id. {}, {}", entity.getId(), entity.getText());
			service.create(entity);
			logger.debug("Created with id.");
		}
		catch(Exception eee)
		{
			logger.debug("Exception", eee);			
			e = eee;
		}
		Assert.assertNotNull("FAIL - expected exception", e);
		Assert.assertTrue("FAIL - expected EntityExistsException", e instanceof SpelEvaluationException);
	}	

	@Test
	public void testUpdate()
	{
		Long id = new Long(1);
		Greeting entity = service.findOne(id);
		Assert.assertNotNull("FAIL - expected entity not null", entity);
		String updatedText = entity.getText() + " test";
		entity.setText(updatedText);
		Greeting updatedEntity = service.update(entity);
		Assert.assertNotNull("FAIL - expected updated entity not null", updatedEntity);
		Assert.assertEquals("FAIL - expected updated entity id attribute unchanged", id, updatedEntity.getId());
		Assert.assertEquals("FAIL - expected updated entity text attribute match", updatedText, updatedEntity.getText());		
	}	
	
	@Test
	public void testUpdateNotFound()
	{
		Exception e = null;
		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setText("Test text");
		Greeting updatedEntity = new Greeting();
		try
		{
			logger.debug("updating not found");	
			updatedEntity = service.update(entity);
			Assert.assertNull("FAIL - expected null", updatedEntity);
		}
		catch(Exception nre)
		{
			logger.debug("Exception", nre);	
			e = nre;
			Assert.assertNotNull("FAIL - expected exception", e);
			Assert.assertTrue("FAIL - expected NoResultException", e instanceof NoResultException);	
		}
	}	

	@Test
	public void testDelete()
	{
		Long id = new Long(1);
		Greeting entity = service.findOne(id);
		Assert.assertNotNull("FAIL - expected entity not null", entity);
		service.delete(id);
		Collection<Greeting> list = service.findAll();
		//Assert.assertEquals("FAIL - expected size", 5, list.size());
		Assert.assertEquals("FAIL - expected size", 2, list.size());
		Greeting deletedEntity = service.findOne(id);
		Assert.assertNull("FAIL - expected entity to be deleted", deletedEntity);		
	}		
	
}
