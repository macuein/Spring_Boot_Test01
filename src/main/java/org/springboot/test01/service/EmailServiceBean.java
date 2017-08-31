package org.springboot.test01.service;

import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springboot.test01.asyncprocs.AsyncResponse;
import org.springboot.test01.model.Greeting;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceBean implements EmailService
{
	private Logger logger = LogManager.getLogger(EmailServiceBean.class.getName());

	@Override
	public Boolean send(Greeting greeting)
	{
		logger.debug(">send");
		Boolean success = Boolean.FALSE;
		
		//simulate method execution time
		long pause = 5000;
		try
		{
			Thread.sleep(pause);
		}
		catch(Exception ex)
		{
			//Do nothing
		}
		logger.debug("processing time was {} seconds", pause/1000);
		
		success = Boolean.TRUE;
		logger.debug("<send");
		return success;
	}

	@Async
	@Override
	public void sendAsync(Greeting greeting)
	{
		logger.debug(">sendAsync");
		try
		{
			send(greeting);
		}
		catch(Exception ex)
		{
			logger.warn("Exception caught tryin to send asynchronous email",ex);
		}
		logger.debug("<sendAsync");
	}

	@Override
	public Future<Boolean> sendAsyncWithResult(Greeting greeting)
	{

		logger.debug(">sendAsyncWithResult");
		AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
		try
		{
			Boolean success = send(greeting);
			response.complete(success);
		}
		catch(Exception ex)
		{
			logger.warn("Exception caught tryin to send asynchronous email with response",ex);
			response.completeExceptionally(ex);
		}
		logger.debug("<sendAsyncWithResult");
		return response;
	}

}
