package org.springboot.test01.quartz01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("Job01")
public class Job01
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected void RunJob() 
	{
		logger.debug("Executing Job01.RunJob ... ");
	}	
	
}
