package com.example.RecipeManager.service;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {
    Recipe addRecipe(Recipe r);
    void deleteRecipe(Long r);
    void editRecipe(Recipe r);
    List<Recipe> getAllRecipes() throws NotFoundException;
    Recipe getRecipe(String name) throws NotFoundException;
    Recipe getRandomRecipe() throws NotFoundException;
    Recipe getRecipeById(Long id) throws Throwable;
    List<Recipe> getMostFittingRecipes(String[] tags);

    Recipe updateRecipe(Recipe r);
}
