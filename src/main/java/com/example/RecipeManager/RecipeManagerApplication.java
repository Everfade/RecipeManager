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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
				registry.addMapping("/recipes/all").allowedOrigins("http://localhost:8081");
			}
		};
	}

	public static void main(String[] args) throws NotFoundException {
		SpringApplication.run(RecipeManagerApplication.class, args);

		Recipe r= new Recipe();
		r.setName("test");
		r.setDescription("test2");
	//	recipeJDBC.addRecipe(r);
		System.out.println( recipeJDBC.getAllRecipes());

	}

}
