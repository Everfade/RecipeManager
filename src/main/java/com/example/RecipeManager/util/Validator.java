package com.example.RecipeManager.util;

import com.example.RecipeManager.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public boolean validateRecipe(){
        return  true;
    }
    public boolean validateTagList(List<Tag> tags){
        if(tags==null)return false;
        if(tags.size()==0)return false;
        return true;
    }
}
