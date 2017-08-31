package org.springboot.test01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springboot.test01.springbatch01.BatchConfig01;
//import org.springboot.test01.springbatchquartz.QuartzConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
//@EnableScheduling
//@Import({BatchConfig01.class})
@EnableAsync
public class Application
{
	static final Logger logger = LogManager.getLogger(Application.class.getName());
	
	public static void main(String[] args) throws Exception
	{
		logger.debug("Starting App ... ");
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CacheManager cacheManager()
	{
		//ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
		GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
		return cacheManager;
	}
	
	//For more than one thread scheduler.
	/*
	@Bean()
	public  ThreadPoolTaskScheduler  taskScheduler()
	{
	    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	    taskScheduler.setPoolSize(2);
	    return  taskScheduler;
	}
	
	OR
	
    	@Override
    	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) 
    	{
        taskRegistrar.setScheduler(taskExecutor());
    	}
 
    	@Bean(destroyMethod="shutdown")
    	public Executor taskExecutor() 
    	{
        return Executors.newScheduledThreadPool(10);
    	}	
	
	*/
	
}
