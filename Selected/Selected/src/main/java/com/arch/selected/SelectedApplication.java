package com.arch.selected;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.arch.selected.dao")
public class SelectedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelectedApplication.class, args);
	}

}
