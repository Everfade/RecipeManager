package com.example.RecipeManager.service;

import com.example.RecipeManager.model.Recipe;
import javassist.NotFoundException;

import java.util.List;

public interface RecipeService {
    void addRecipe(Recipe r);
    void deleteRecipe(Recipe r);
    void editRecipe(Recipe r);
    List<Recipe> getAllRecipes() throws NotFoundException;
    Recipe getRecipe(String name);
}
