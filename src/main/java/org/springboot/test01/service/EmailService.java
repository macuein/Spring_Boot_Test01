package org.springboot.test01.service;

import java.util.concurrent.Future;

import org.springboot.test01.model.Greeting;

public interface EmailService
{
	
	Boolean send(Greeting greeting);
	
	void sendAsync(Greeting greeting);
	
	Future<Boolean> sendAsyncWithResult(Greeting greeting);
	
	
}
