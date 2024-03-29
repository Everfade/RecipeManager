package com.example.RecipeManager.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Recipe implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(nullable = true,updatable = false)
    private Long id;



    private String name;
    private String imageData;
    private String description;
    @Column
    private String ingredients;
    @ElementCollection
    private List<String> instructions;
    @ElementCollection
    private List<Integer> tags= new LinkedList<>() ;

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public List<Integer> getTags() {
        return tags;
    }
    public  int returnFirstTag(){
        if(tags.size()==0)return -1;

        try {
            return this.tags.get(0);
        }
        catch (Exception e){
            return -1;
        }
    }
   public void addTagId(int id){
        if(tags.contains(id))return;;
        this.tags.add(id);
   }
    public List<String> getInstructions() {
        return instructions;
    }


    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public Recipe(){};

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Recipe {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredient='" + ingredients + '\'' +"tags= "+ tags.toString()  +"}";}

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String   imageData) {
        this.imageData = imageData;
    }
}
