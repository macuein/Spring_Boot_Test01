package org.springboot.test01.springbatch01;

import org.springboot.test01.model.Greeting;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@Profile("batch3")
public class BatchConfig01
{
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	    
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job addNewJob()
	{
		return null;
		//return jobBuilderFactory.get("addNewJob")
				//.listener()
				//.start(step())
				//.build();
	}
	
	@Bean
	public Step step()
	{
		return null;
		//return stepBuilderFactory.get("step")
				//.<T,T>chunk(1) //important to be one in this case to commit after every line read
				//.reader()
				//.processor()
				//.writer()
				//.listener()
				//.skipLimit(10) //default is set to 0
				//.skip()
				//.build();
	}		

	@Bean
	public ItemReader<Greeting> reader()
	{
		//FlatFileItemReader<Greeting> reader = new FlatFileItemReader<Greeting>();
		//reader.setLinesToSkip(1);//first line is title definition 
		//reader.setResource(new ClassPathResource("greetings.txt"));
		//reader.setLineMapper(lineMapper());
		//return reader; 
		return null;
	}	

	@Bean
	public LineMapper<Greeting> lineMapper() 
	{
		//DefaultLineMapper<Greeting> lineMapper = new DefaultLineMapper<Greeting>();
		//DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		//lineTokenizer.setDelimiter(";");
		//lineTokenizer.setStrict(false);
		//lineTokenizer.setNames(new String[]{"GREETING"});
		//BeanWrapperFieldSetMapper<Greeting> fieldSetMapper = new BeanWrapperFieldSetMapper<Greeting>();
		//fieldSetMapper.setTargetType(Greeting.class);
		//lineMapper.setLineTokenizer(lineTokenizer);
		//lineMapper.setFieldSetMapper(greetingFieldSetMapper());
		//return lineMapper;
		
		return null;
	}

/*	
	@Bean
	public GreetingFieldSetMapper greetingFieldSetMapper() 
	{
		return new GreetingFieldSetMapper();
	}	

	@Bean
	public ItemProcessor<Greeting, Greeting> processor() 
	{
		return new GreetingItemProcessor();
	}	

	@Bean
	public ItemWriter<Greeting> writer() 
	{
		return new Writer();
	}
	    
	@Bean
	public ProtocolListener protocolListener()
	{
		return new ProtocolListener();
	}
	 
	@Bean
	public LogProcessListener logProcessListener()
	{
		return new LogProcessListener();
	}    
*/	
	
}
