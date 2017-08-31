package org.springboot.test01.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport 
{

	static final Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class.getName());
	
	@Autowired
	public JobCompletionNotificationListener() 
	{

	}

	@Override
	public void afterJob(JobExecution jobExecution) 
	{
        	logger.info(String.format("*********** job instance Id: %d", jobExecution.getJobInstance().getId()));		
        	logger.info(String.format("********* Name of the job %s", jobExecution.getJobInstance().getJobName()));
        	logger.info(String.format("*********** Job status: %s", jobExecution.getStatus()));
        	logger.info(String.format("*********** Exit status: %s", jobExecution.getExitStatus().getExitCode()));
	}
	
	
}
