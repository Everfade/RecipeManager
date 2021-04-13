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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(RecipeEndpoint.BASE_URL)
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

    @GetMapping("/tags/all")
    public ResponseEntity<List<Tag>> getAllTags() throws NotFoundException {
        LOGGER.info("GET " + BASE_URL + "/");
        List<Tag> t= ts.getAllTags();
        return  new ResponseEntity<>(t, HttpStatus.OK);
    }

}
