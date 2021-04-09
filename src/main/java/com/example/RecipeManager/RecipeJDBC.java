package com.example.RecipeManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

public class RecipeJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;


}
