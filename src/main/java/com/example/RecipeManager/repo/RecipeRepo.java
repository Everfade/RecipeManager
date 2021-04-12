package com.example.RecipeManager.repo;

import com.example.RecipeManager.model.Recipe;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface RecipeRepo  extends JpaRepository<Recipe,Long> {


    void deleteRecipeById(Long id);

   Recipe findRecipeById(Long id);

 //   Recipe updateRecipe(Recipe r);
}
