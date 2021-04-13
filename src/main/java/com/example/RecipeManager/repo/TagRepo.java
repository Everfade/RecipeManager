package com.example.RecipeManager.repo;

import com.example.RecipeManager.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepo extends JpaRepository<Tag, Long> {


}
