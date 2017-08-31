package org.springboot.test01.springbatchquartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


public class MyTasklet implements Tasklet
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RepeatStatus execute(StepContribution step, ChunkContext chunk) throws Exception
	{
		logger.debug("Running MyTasklet ... ");

		return RepeatStatus.FINISHED;
	}

	
}
