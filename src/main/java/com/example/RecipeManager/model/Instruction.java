package com.example.RecipeManager.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Instruction {
    @javax.persistence.Id
    private Long id;
    private String instruction;
    private int orderNr;
    public void setId(Long id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    @Id
    public Long getId() {
        return id;
    }
}
