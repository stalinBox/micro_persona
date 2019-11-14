package ec.gob.mag.rna.personas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableResourceServer
@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MicroRnaProductores extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroRnaProductores.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<MicroRnaProductores> applicationClass = MicroRnaProductores.class;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

}
