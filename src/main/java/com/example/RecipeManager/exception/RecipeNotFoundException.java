package com.example.RecipeManager.exception;

public class RecipeNotFoundException extends  RuntimeException{
    public RecipeNotFoundException(String s) {
        super(s);
    }
}
