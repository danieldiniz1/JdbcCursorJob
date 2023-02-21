package br.com.training.jdbcCursorReaderJob.reader;

import br.com.training.jdbcCursorReaderJob.model.Cliente;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class jdbcCursorReaderConfig {

    @Bean // reader sem paginação
    public JdbcCursorItemReader<Cliente> jdbcCursorReader(
            @Qualifier("appDataSource") DataSource appDataSource){
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("jdbcCursorReader")
                .dataSource(appDataSource)
                .sql("SELECT * FROM cliente")
                .rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
                .build();
    }

    @Bean //reader com paginação
    public JdbcPagingItemReader<Cliente> jdbcPagingReader(
            @Qualifier("appDataSource") DataSource appDataSource,
            PagingQueryProvider queryProvider){
        return new JdbcPagingItemReaderBuilder<Cliente>()
                .name("jdbcPagingReader")
                .dataSource(appDataSource)
                .queryProvider(queryProvider)
                .pageSize(1)
                .rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
                .build();

    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource") DataSource dataSource){
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT *");
        queryProvider.setFromClause("FROM cliente");
        queryProvider.setSortKey("email");
        return queryProvider;
    }

}
