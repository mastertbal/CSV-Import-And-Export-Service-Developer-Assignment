package com.mastertbal.csvbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "CSV Import and Export Backend Application.",
				description = "A csv backend application for importing student entities, persisting them into the database, and exporting them also in the same csv file format.",
				version = "v1",
				contact = @Contact(
						name = "Balogun Oluwatobi",
						email = "olutobitbal@gmail.com"
				)
		)
)
public class CsvbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvbackendApplication.class, args);
	}

}
