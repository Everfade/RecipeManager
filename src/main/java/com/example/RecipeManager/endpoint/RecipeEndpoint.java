package com.example.RecipeManager.endpoint;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.service.RecipeService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(RecipeEndpoint.BASE_URL)
@CrossOrigin(origins = "*", maxAge = 3600)//goddamn that cost so much time
public class RecipeEndpoint {
    static final String BASE_URL = "/recipes";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeService recipeService;

    @Autowired
    public RecipeEndpoint(RecipeService recipeService) {
        LOGGER.info("success");
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() throws NotFoundException {
        LOGGER.info("GET " + BASE_URL + "/");
         List<Recipe> r= recipeService.getAllRecipes();
        return  new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe r){
        Recipe recipe   = recipeService.addRecipe(r);
        return new ResponseEntity<>(recipe,HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe r){
        Recipe recipe   = recipeService.updateRecipe(r);
        return new ResponseEntity<>(recipe,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id) throws Throwable {
        LOGGER.info("GET " + BASE_URL + "/");
        recipeService.deleteRecipe(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Recipe> getAllRecipes(@PathVariable("id") Long id) throws Throwable {
        LOGGER.info("GET " + BASE_URL + "/");
        Recipe r= recipeService.getRecipeById(id);
        return  new ResponseEntity<>(r,HttpStatus.OK);
    }
}
