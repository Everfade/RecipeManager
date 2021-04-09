package com.example.RecipeManager.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
public class Recipe implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(nullable = true,updatable = false)
    private Long id;
    private  String name;
    private String ingredient;
    public Recipe(){};


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingrediant) {
        this.ingredient = ingredient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
