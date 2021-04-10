package com.example.RecipeManager.service;

import com.example.RecipeManager.dao.RecipeDao;
import com.example.RecipeManager.dao.RecipeJDBC;
import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.util.Validator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImp implements  RecipeService{
    private final RecipeJDBC dao;
    private final Validator validator;

    @Autowired
    public RecipeServiceImp(RecipeJDBC r, Validator validator) {
        this.dao = r;
        this.validator = validator;
    }
    @Override
    public void addRecipe(Recipe r) {

    }

    @Override
    public void deleteRecipe(Recipe r) {

    }

    @Override
    public void editRecipe(Recipe r) {

    }

    @Override
    public List<Recipe> getAllRecipes() throws NotFoundException {
            return dao.getAllRecipes();
    }

    @Override
    public Recipe getRecipe(String name) {
     return dao.getRecipe(name);
    }
}
