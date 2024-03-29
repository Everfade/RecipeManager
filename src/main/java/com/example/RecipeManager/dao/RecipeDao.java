package com.example.RecipeManager.dao;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;

import java.sql.Blob;
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
  //  void addTagsToRecipe(List<Tag> tags, Recipe r);
     void addTagsToRecipe(List<Integer> tags, Recipe r);
    Recipe getRecipeById(Long id) throws NotFoundException;

    void deleteRecipeById(Long id);

    void addImage(Long id, String fileContent);
      String getImage(Long id) throws NotFoundException;
}
