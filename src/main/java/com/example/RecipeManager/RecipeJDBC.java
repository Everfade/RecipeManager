package com.example.RecipeManager;


import com.example.RecipeManager.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Service
public class RecipeJDBC implements RecipeDao {
    private static final String TABLE_NAME = "Recipe";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    public  RecipeJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public void addRecipe(Recipe r) {
        LOGGER.trace("addRecipe({})", r.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql;
        sql = "INSERT INTO " + TABLE_NAME + " (name,description ) VALUES(?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,r.getName());
            stmt.setString(2, r.getDescription());
        //    stmt.setString(3, r.getIngredient());
            return stmt;
        }, keyHolder);

    }
}
