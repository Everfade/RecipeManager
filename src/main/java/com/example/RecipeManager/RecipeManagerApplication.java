package com.example.RecipeManager;

import com.example.RecipeManager.dao.RecipeJDBC;
import com.example.RecipeManager.model.Recipe;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories("com.example.RecipeManager.repo")
public class RecipeManagerApplication {

      static RecipeJDBC recipeJDBC = null;
	@Autowired
	public RecipeManagerApplication(RecipeJDBC recipeJDBC) {
		this.recipeJDBC = recipeJDBC;
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/recipes/all").allowedOrigins("http://localhost:4200");
			}
		};
	}
	/*@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}*/
	public static void main(String[] args) throws NotFoundException {
		SpringApplication.run(RecipeManagerApplication.class, args);

		Recipe r= new Recipe();
		r.setName("test");
		r.setDescription("test2");
	//	recipeJDBC.addRecipe(r);
		System.out.println( recipeJDBC.getAllRecipes());

	}

}
