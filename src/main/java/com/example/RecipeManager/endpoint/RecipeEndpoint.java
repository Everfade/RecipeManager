package com.example.RecipeManager.endpoint;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
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
        LOGGER.info("GET " + BASE_URL + "/all");
         List<Recipe> r= recipeService.getAllRecipes();
        return  new ResponseEntity<>(r,HttpStatus.OK);
    }
    @GetMapping("/random")
    public ResponseEntity<Recipe> getRandomRecipes() throws NotFoundException {
        LOGGER.info("GET " + BASE_URL + "/random");
        Recipe r= recipeService.getRandomRecipe();
        return  new ResponseEntity<>(r,HttpStatus.OK);
    }
    @GetMapping("/search/results")
    public ResponseEntity<List<Recipe>>filterByTags( @RequestParam(name="tags",defaultValue = "") String tags) throws NotFoundException{
        String[] searchTags= tags.split(",");
        LOGGER.info("GET " + BASE_URL + "/search/results");
        List<Recipe> r= recipeService.getMostFittingRecipes(searchTags);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe r){
        LOGGER.info("POST "+ BASE_URL+"/add "+ r.getName()+" "+r.getInstructions().toString());
        recipeService.addRecipe(r);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe r){
        LOGGER.info("PUT"+BASE_URL+"/UPDATE "+r.getName()+" "+r.getTags()+" "+r.getIngredients());
        recipeService.editRecipe(r);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/addImage")
    public ResponseEntity<Recipe> updateImage(@RequestBody Recipe r){
        LOGGER.info("PUT"+BASE_URL+"/UPDATEImage for "+r.getName()+" "+r.getTags()+" "+r.getIngredients());
        if(r.getImageData()==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        recipeService.updateImage(r);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") Long id) throws Throwable {
        LOGGER.info("DELETE " + BASE_URL + "/delete/"+id);
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
