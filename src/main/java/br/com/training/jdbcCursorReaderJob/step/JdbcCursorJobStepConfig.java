package br.com.training.jdbcCursorReaderJob.step;

import br.com.training.jdbcCursorReaderJob.model.Cliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JdbcCursorJobStepConfig {

    @Bean
    public Step jdbcCursorJobStep(ItemReader<Cliente> jdbcPagingReader,
                                  ItemWriter<Cliente> jdbcCursorWriter,
                                  JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager){
        return new StepBuilder("jdbcCursorJobStep",jobRepository)
                .<Cliente,Cliente> chunk(1)
                .reader(jdbcPagingReader)
                .writer(jdbcCursorWriter)
                .transactionManager(transactionManager)
                .build();
    }
}
