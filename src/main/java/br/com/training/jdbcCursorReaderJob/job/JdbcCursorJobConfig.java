package br.com.training.jdbcCursorReaderJob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcCursorJobConfig {

    @Bean
    public Job JdbcCursorJob(Step jdbcCursorJobStep, JobRepository jobRepository){
        return new JobBuilder("JdbcCursorJob",jobRepository)
                .start(jdbcCursorJobStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
