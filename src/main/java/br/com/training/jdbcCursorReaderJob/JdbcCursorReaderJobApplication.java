package br.com.training.jdbcCursorReaderJob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcCursorReaderJobApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JdbcCursorReaderJobApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
