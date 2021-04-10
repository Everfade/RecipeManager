package com.example.RecipeManager.dao;

import com.example.RecipeManager.model.Recipe;
import javassist.NotFoundException;

import java.util.List;

public interface RecipeDao {
    void addRecipe(Recipe r);
    void deleteRecipe(Recipe r);
    void editRecipe(Recipe r);
    Recipe getRecipe(String name);
    List<Recipe> getAllRecipes() throws NotFoundException;

}
