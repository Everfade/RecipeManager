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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
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
	public static void main(String[] args) throws NotFoundException, IOException {
	 	SpringApplication.run(RecipeManagerApplication.class, args);
	//	File fi = new File("D:/RPcode/RecipeManager/Frontend/RecipeManagerApp/Src/assets/palm1.png");
		//byte[] fileContent = Files.readAllBytes(fi.toPath());
	////recipeJDBC.addImage(44l,encodedString);

	//	recipeJDBC.addRecipe(r);
		//	System.out.println( recipeJDBC.getAllRecipes());
		System.out.println("System up and running...");


	}

}
