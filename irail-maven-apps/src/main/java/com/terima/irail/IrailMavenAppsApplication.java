package com.terima.irail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages= {"com.terima.irail"})
public class IrailMavenAppsApplication extends SpringBootServletInitializer{
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IrailMavenAppsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(IrailMavenAppsApplication.class, args);
	}

}

