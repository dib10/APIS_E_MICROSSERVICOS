package dev.caio.tasks_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // classe de configuração
public class AppConfig {
	
	@Bean // produz um bean 
	public ModelMapper modelMapper () {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
