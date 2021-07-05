package com.example.RecipeManager.dao;


import com.example.RecipeManager.model.Instruction;
import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
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
import java.util.LinkedList;
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
        sql = "INSERT INTO " + TABLE_NAME + " (name,description,instructions ) VALUES(?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,r.getName());
            stmt.setString(2, r.getDescription());
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
        /*select * from recipe INNER JOIN
RECIPEINSTRUCTIONS ON RECIPE.ID=RECIPEINSTRUCTIONS.repid
JOIN INSTRUCTION  ON  RECIPEINSTRUCTIONS.INID=
INSTRUCTION.ID WHERE RECIPE.ID=1
 */
    }

    @Override
    public List<Recipe> getAllRecipes() throws NotFoundException {
        LOGGER.trace("getAllRecipes");
        final String sql = "SELECT name,description,id FROM " + TABLE_NAME ;
        List<Recipe> recipes = jdbcTemplate.query(sql, this::mapRow);
        if (recipes.isEmpty()) throw new NotFoundException("No Recipes in Database");
        return recipes;
    }

    @Override
    public Recipe getRandomRecipe() throws NotFoundException {
        LOGGER.trace("getRandomRecipe with name ");
        final String sql = "SELECT * FROM " + TABLE_NAME +" ORDER BY RANDOM() LIMIT 1 ";
        List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow);
        if (r.isEmpty()) throw new NotFoundException("Database is empty");
        getInstructions(r.get(0));
        return r.get(0);
    }
    @Override
    public void getInstructions(Recipe p) {
          String sql = "select instruction.instruction from recipe INNER JOIN RECIPE_INSTRUCTIONS ON" +
                  " RECIPE.ID = RECIPE_INSTRUCTIONS.repid JOIN INSTRUCTION  ON" +
                  "  RECIPE_INSTRUCTIONS.INID = INSTRUCTION.ID WHERE RECIPE.ID = ?";

        LOGGER.info(sql.toString());

        List<Instruction> instructions=  jdbcTemplate.query(sql,this::mapInstruction,p.getId());
        LOGGER.info("_________________________________________________");
        LOGGER.info(instructions.toString());
       LinkedList<String> instText = new LinkedList<>();
        for (Instruction i:instructions) {

            instText.add(i.getInstruction());
        }
        p.setInstructions( instText);



    }

    @Override
    public Recipe getRecipeById(Long id) throws NotFoundException {
        LOGGER.trace("getAllRecipe with id "+id);
        final String sql = "SELECT * FROM " + TABLE_NAME +" WHERE id = ? ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow,id);
        if (r.isEmpty()) throw new NotFoundException("Could not find recipe with name " + id);
        LOGGER.info( r.get(0).toString());
        getInstructions(r.get(0));
        return r.get(0);
    }

    @Override
    public List<Recipe> getMostFittingRecipes(String[] tags) {
        //ugly hack needs to be written with prepared statment
          String sql="  select distinct recipe.name,recipe.id,recipe.description from recipe INNER JOIN recipetags" +
                 " ON RECIPE.id =recipetags.repid JOIN" +
                 " tag  ON  recipetags.tagid = tag.ID WHERE tag.name = '"+tags[0]+"'";

         String criteraTags="";

         if(tags.length>1) {
             for(String t: tags){
                 if(!sql.contains(t)) {
                     sql += " OR tag.name ='" + t+"'";
                 }
                 criteraTags+= t+",";
             }
             criteraTags = criteraTags.substring(0, criteraTags.length() - 2);
         }
         else{
             criteraTags=tags[0];
         }
        LOGGER.info(sql);
        List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow);

         return  r;
    }



    private Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
        final Recipe r = new Recipe();
        r.setName(resultSet.getString("name"));
        r.setDescription(resultSet.getString("description"));
      //  r.setInstructions(resultSet.getString("instructions"));
    //    r.setIngredient(resultSet.getString("ingredient"));
        r.setId(resultSet.getLong("id"));
        return r;
    }
    private Instruction mapInstruction(ResultSet resultSet, int i) throws SQLException {
        final Instruction instruction= new Instruction();
        instruction.setInstruction(resultSet.getString("instruction"));

        return instruction;
    }
}
