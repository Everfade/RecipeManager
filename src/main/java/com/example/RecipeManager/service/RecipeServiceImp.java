package com.example.RecipeManager.service;

import com.example.RecipeManager.dao.RecipeJDBC;
import com.example.RecipeManager.exception.RecipeNotFoundException;
import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import com.example.RecipeManager.repo.RecipeRepo;
import com.example.RecipeManager.util.Validator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

 @Service
public class RecipeServiceImp implements  RecipeService{
    private final RecipeJDBC dao;
    private final Validator validator;
    private  final RecipeRepo repo;

    @Autowired
    public RecipeServiceImp(RecipeJDBC r, Validator validator, RecipeRepo repo) {
        this.dao = r;
        this.validator = validator;
        this.repo=repo;
    }


    public Recipe addRecipe(Recipe r) {
        System.out.println( repo.save(r));
        return r;
    }

    @Override
    public void deleteRecipe(Long id) {
        repo.deleteRecipeById(id);
    }

    @Override
    public void editRecipe(Recipe r) {

    }

    @Override
    public List<Recipe> getAllRecipes() throws NotFoundException {
            return dao.getAllRecipes();
    }
    public  Recipe getRecipeById(Long id) throws Throwable {
        return  dao.getRecipeById(id);

    }

     @Override
     public List<Recipe> getMostFittingRecipes(String[] tags) {
         return dao.getMostFittingRecipes(tags);
     }

     @Override
    public Recipe updateRecipe(Recipe r) {
    //
        return  null;
    }

    @Override
    public Recipe getRecipe(String name) throws NotFoundException {
     return dao.getRecipe(name);
    }

     @Override
     public Recipe getRandomRecipe() throws NotFoundException {
         return dao.getRandomRecipe();
     }
 }
