package com.example.RecipeManager.dao;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;

import java.util.List;

public interface RecipeDao {
    void addRecipe(Recipe r);
    void deleteRecipe(Recipe r);
    void editRecipe(Recipe r);
    Recipe getRecipe(String name) throws NotFoundException;
    List<Recipe> getAllRecipes() throws NotFoundException;
    Recipe getRandomRecipe() throws NotFoundException;
    List<Recipe> getMostFittingRecipes(String[] tags);
    void getInstructions(Recipe p);

    Recipe getRecipeById(Long id) throws NotFoundException;
}
