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
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class jdbcCursorReaderConfig {

    @Bean // reader sem paginação
    public JdbcCursorItemReader<Cliente> jdbcCursorReader(
            @Qualifier("appDataSource") DataSource appDataSource){
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("jdbcCursorReader")
                .dataSource(appDataSource)
                .sql("SELECT * FROM cliente")
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<Cliente> rowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.getRow() == 11){
                    throw new SQLException(String.format("Encerrando a execução -  Cliente Inválido %s",rs.getString("email")));
                }
                else return clienteRowMapper(rs);
            }

            private Cliente clienteRowMapper(ResultSet rs) throws SQLException {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setSobrenome(rs.getString("sobrenome"));
                cliente.setIdade(rs.getString("idade"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }
        };
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
