/*
 * ICQRF NUOVA VERSIONE
 * LEONARDO CYS SPA
 */
package test;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "Test MS API", version = "v1"))
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class TestApp {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TestApp.class, args);
	}
 
	/**
	 * Model mapper.
	 *
	 * @return the model mapper
	 */
	 

	 /**
	 * Cors configurer.
	 *
	 * @return the web mvc configurer
	 */
 	@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("*")
					.allowedHeaders("*")
					.exposedHeaders("Content-Disposition");
				}
			};
		}

	/**
	 * Method validation post processor.
	 *
	 * @return the method validation post processor
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	
	@Bean
	public SimpleModule emptyStringAsNullModule() {
		SimpleModule module = new SimpleModule();

		module.addDeserializer(String.class, new StdDeserializer<String>(String.class) {

			private static final long serialVersionUID = -7433315809728563191L;

			@Override
			public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
				String result = StringDeserializer.instance.deserialize(parser, context);
				if (ObjectUtils.isEmpty(result)) {
					return null;
				}

				result = result.strip();

				if (ObjectUtils.isEmpty(result)) {
					return null;
				}

				return result;
			}
		});

		return module;
	}
}
