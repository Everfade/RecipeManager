package com.example.RecipeManager.service;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;

import java.util.List;

public interface TagService {
    void addTag(Tag t);
    void deleteTag(long id);
    void editTag(Tag t);

    List<Tag> getAllTags() throws NotFoundException;

    void updateTag(Tag t);
}
