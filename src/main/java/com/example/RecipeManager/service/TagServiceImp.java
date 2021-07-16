package com.example.RecipeManager.service;

import com.example.RecipeManager.dao.TagJDBC;
import com.example.RecipeManager.model.Tag;
import com.example.RecipeManager.repo.TagRepo;
import com.example.RecipeManager.util.Validator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagServiceImp implements  TagService{
    private final TagJDBC dao;
    private final Validator validator;
    private  final TagRepo repo;

    @Autowired
    public TagServiceImp    (TagJDBC r, Validator validator, TagRepo repo) {
        this.dao = r;
        this.validator = validator;
        this.repo=repo;
    }


    @Override
    public void addTag(Tag t) {
        dao.addTag(t);
    }

    @Override
    public void deleteTag(long id) {
        this.dao.deleteTag(id);

    }

    @Override
    public void editTag(Tag t) {

    }

    @Override
    public List<Tag> getAllTags() throws NotFoundException {
        return  dao.getAllTags();
    }

    @Override
    public void updateTag(Tag t) {
         this.dao.updateTag(t);
    }
}
