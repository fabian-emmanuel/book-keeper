package com.findar.bookkeeper;

import com.findar.bookkeeper.config.properties.ApiProperty;
import com.findar.bookkeeper.config.properties.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@EnableTransactionManagement
@EnableConfigurationProperties(value = {JWTProperty.class, ApiProperty.class})
public class BookKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookKeeperApplication.class, args);
	}

}
