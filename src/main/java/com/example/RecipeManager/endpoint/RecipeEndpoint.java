package com.example.RecipeManager.endpoint;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.service.RecipeService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(RecipeEndpoint.BASE_URL)
public class RecipeEndpoint {
    static final String BASE_URL = "/recipes";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeService recipeService;


    @Autowired
    public RecipeEndpoint(RecipeService recipeService) {
        LOGGER.info("success");
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() throws NotFoundException {
        LOGGER.info("GET " + BASE_URL + "/");
            return recipeService.getAllRecipes();
    }
}
