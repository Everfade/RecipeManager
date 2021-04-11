package com.example.RecipeManager.dao;


import com.example.RecipeManager.model.Recipe;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
        sql = "INSERT INTO " + TABLE_NAME + " (name,description,instructions ) VALUES(?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,r.getName());
            stmt.setString(2, r.getDescription());
            stmt.setString(3,r.getInstructions());
        //    stmt.setString(3, r.getIngredient());
            return stmt;
        }, keyHolder);

    }
    @Override
    public void deleteRecipe(Recipe r) {

    }

    @Override
    public void editRecipe(Recipe r) {

    }

    @Override
    public Recipe getRecipe(String name) throws NotFoundException {
        LOGGER.trace("getAllRecipe with name "+name);
        final String sql = "SELECT * FROM " + TABLE_NAME +"WHERE NAME = ? ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
      List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow,name);
        if (r.isEmpty()) throw new NotFoundException("Could not find recipe with name " + name);
        return r.get(0);
    }

    @Override
    public List<Recipe> getAllRecipes() throws NotFoundException {
        LOGGER.trace("getAllRecipes");
        final String sql = "SELECT * FROM " + TABLE_NAME ;
        List<Recipe> recipes = jdbcTemplate.query(sql, this::mapRow);
        if (recipes.isEmpty()) throw new NotFoundException("No Recipes in Database");
        return recipes;
    }

    private Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
        final Recipe r = new Recipe();
        r.setName(resultSet.getString("name"));
        r.setDescription(resultSet.getString("description"));
        r.setInstructions(resultSet.getString("instructions"));
    //    r.setIngredient(resultSet.getString("ingredient"));
        r.setId(resultSet.getLong("id"));
        return r;
    }

}
