package com.mosoftvn;

import com.mosoftvn.config.StorageProperties;
import com.mosoftvn.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class) // thêm cấu hình storage
public class LtDiDongApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtDiDongApiApplication.class, args);
	}

	// thêm cấu hình storage
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
}
