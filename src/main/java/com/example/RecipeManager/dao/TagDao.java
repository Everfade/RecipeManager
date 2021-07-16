package com.example.RecipeManager.dao;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;

import java.util.List;

public interface TagDao {
    void addTag(Tag r);
    void deleteTag(long id);
    void updateTag(Tag t);
    List<Tag> getAllTags() throws NotFoundException;


}
