package org.springboot.test01.springbatch;

import java.util.Collection;

import org.springboot.test01.model.Greeting;
import org.springboot.test01.service.GreetingService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableBatchProcessing
@Profile("batch2")
public class BatchConfiguration
{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	private GreetingService greetingService;
	private Collection<Greeting> greetings;	

	// tag::readerwriterprocessor[]
//	@Bean
//	public GreetingItemReader reader() 
//	{
//		return new GreetingItemReader();
//	}
	@Bean
	public ItemReader<Greeting> reader() 
	{
		greetings = greetingService.findAll();
		IteratorItemReader<Greeting> itereader = new IteratorItemReader<Greeting>(greetings);
		return itereader;
	}
	
	@Bean
	public GreetingItemProcessor processor() 
	{
		return new GreetingItemProcessor();
	}
	
	@Bean
	public GreetingItemWriter writer() 
	{
		return new GreetingItemWriter();
	}
	// end::readerwriterprocessor[]
	
	// tag::listener[]

	@Bean
	public JobExecutionListener listener() 
	{
		return new JobCompletionNotificationListener();
	}

	// end::listener[]	

	// tag::jobstep[]
	@Bean
	public Job importUserJob() 
	{
		return jobBuilderFactory.get("greetingsJob")
	                			.incrementer(new RunIdIncrementer())
	                			.listener(listener())
	                			.flow(step1())
	                			.end()
	                			.build();
	}

	@Bean
	public Step step1() 
	{
		return stepBuilderFactory.get("step1")
							.<Greeting, Greeting> chunk(5)
							.reader(reader())
							.processor(processor())
							.writer(writer())
							.build();
	}
	// end::jobstep[]	
	
}
