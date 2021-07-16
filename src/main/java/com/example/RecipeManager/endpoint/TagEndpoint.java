package com.example.RecipeManager.endpoint;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import com.example.RecipeManager.service.TagService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(TagEndpoint.BASE_URL)
@CrossOrigin(origins = "*", maxAge = 3600)
public class TagEndpoint {
    static final String BASE_URL = "/tags";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TagService ts;

    @Autowired
    public TagEndpoint(TagService ts) {
        LOGGER.info("success");
        this.ts = ts;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags() throws NotFoundException {
        LOGGER.info("GET " + BASE_URL + "/");
        List<Tag> t= ts.getAllTags();
        return  new ResponseEntity<>(t, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Tag> postTag(@RequestBody Tag t) throws NotFoundException {
        LOGGER.info("POST " + BASE_URL + "/"+t.getName());
        ts.addTag(t);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Tag> putTag(@RequestBody Tag t) throws  NotFoundException{
        LOGGER.info("PUT "+BASE_URL+"/"+t.getId()+", "+t.getName());
        ts.updateTag(t);
        return  new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") Long id) throws Throwable {
        LOGGER.info("DELETE " + BASE_URL + "/delete/"+id);
        ts.deleteTag(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
