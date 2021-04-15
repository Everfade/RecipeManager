package com.example.RecipeManager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Recipe implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(nullable = true,updatable = false)
    private Long id;
    private String name;
    private String description;
    private String ingredients;
    @ElementCollection
    private List<String> instructions;

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
                ", ingredient='" + ingredients + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredient) {
        this.ingredients = ingredients;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
