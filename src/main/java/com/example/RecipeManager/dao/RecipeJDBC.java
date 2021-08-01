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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        final String sql= "INSERT INTO " + TABLE_NAME + " (name,description,ingredients ) VALUES(?,?,?) RETURNING ID";
        List<Integer> recipeIds=jdbcTemplate.query(sql,this::mapIds,r.getName(),r.getDescription(),r.getIngredients());
        List<Integer> instructionIds=new LinkedList<>();
        int orderNr=1;
        final String sqlInstr="INSERT INTO INSTRUCTION (INSTRUCTION, ORDERNR) VALUES(?,?) RETURNING ID";
        while(r.getInstructions().size()>0){
            List<Integer> temp=jdbcTemplate.query(sqlInstr,this::mapIds,r.getInstructions().get(0),orderNr);
            instructionIds.add(temp.get(0));
            r.getInstructions().remove(0);
            orderNr++;

        }
      insertRecipeInstruction(recipeIds.get(0),instructionIds);

    }

    private void insertRecipeInstruction(Integer recipeId, List<Integer> instructionIds) {

        for (int id: instructionIds){
            LOGGER.trace("addInstructionRelation({})"+ recipeId+", "+instructionIds);
            final String sql= "INSERT INTO RECIPE_INSTRUCTIONS (REPID,INID ) VALUES(?,?)";
            jdbcTemplate.update(sql,recipeId,id);
        }
    }

    private void insertRecipeInstruction(Long repid, List<String> instructions) {
        for (String inst: instructions){
            LOGGER.trace("addInstructionRelation({})"+ repid+", "+instructions);
            final String sql= "INSERT INTO RECIPE_INSTRUCTIONS (REPID,INID ) VALUES(?,?)";
            jdbcTemplate.update(sql,repid,inst);
        }
    }



    @Override
    public void editRecipe(Recipe r) {
        LOGGER.trace("UPDATE RECIPE with name "+ r.getName());
        LOGGER.info(r.getTags().toString());
        //update recipe
        final String sql = "UPDATE  " + TABLE_NAME +"" +
                " SET NAME= ?, DESCRIPTION=? , INGREDIENTS=? WHERE ID = ? ";
        jdbcTemplate.update(sql,r.getName(),r.getDescription(),r.getIngredients(),r.getId());
        //delte old relation
        final String sql2 = "DELETE FROM RECIPE_INSTRUCTIONS WHERE REPID=?; DELETE FROM RECIPETAGS WHERE REPID=?";
        jdbcTemplate.update(sql2,r.getId(),r.getId());
        //clean up unused instructions
        final String sql3="DELETE FROM instruction " +
                " WHERE NOT EXISTS(SELECT NULL " +
                "                    FROM RECIPE_INSTRUCTIONS R " +
                "                )";
        jdbcTemplate.update(sql3);
        this.addTagsToRecipe(r.getTags(),r);

        List<Integer> instructionIds=new LinkedList<>();
        int orderNr=1;
        final String sqlInstr="INSERT INTO INSTRUCTION (INSTRUCTION, ORDERNR) VALUES(?,?) RETURNING ID";
        if(r.getInstructions()==null)return;
        while(r.getInstructions().size()>0){
            r.getInstructions().set(0, r.getInstructions().get(0).replace("•",""));
            List<Integer> temp=jdbcTemplate.query(sqlInstr,this::mapIds,r.getInstructions().get(0),orderNr);
            instructionIds.add(temp.get(0));
            r.getInstructions().remove(0);
            orderNr++;

        }
        insertRecipeInstruction(Math.toIntExact(r.getId()),instructionIds);

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
        final String sql = "select recipe.name, recipe.id, recipe.description,recipe.ingredients,"
        +"array_agg(tagid) FROM RECIPETAGS RIGHT JOIN RECIPE ON REPID=RECIPE.ID "+
        "GROUP BY RECIPE.NAME,RECIPE.ID,RECIPE.DESCRIPTION";
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
          final String sql = "select instruction.instruction,instruction.orderNr from recipe INNER JOIN RECIPE_INSTRUCTIONS ON" +
                  " RECIPE.ID = RECIPE_INSTRUCTIONS.repid JOIN INSTRUCTION  ON" +
                  "  RECIPE_INSTRUCTIONS.INID = INSTRUCTION.ID WHERE RECIPE.ID = ?";

        LOGGER.info(sql.toString());

        List<Instruction> instructions=  jdbcTemplate.query(sql,this::mapInstruction,p.getId());
        LOGGER.info("_________________________________________________");
        LOGGER.info(instructions.toString());
       LinkedList<String> instText = new LinkedList<>();
        for (Instruction i:instructions) {

            instText.add(i.getInstruction().replace("•",""));
        }
        p.setInstructions( instText);



    }
    @Override
    public void addTagsToRecipe(List<Integer> tags, Recipe r) {
        try {
            while (tags.size() > 0) {
                LOGGER.info(tags.get(0).toString());
                String sql = "INSERT INTO RECIPETAGS (REPID,TAGID) VALUES(?,?)";
                jdbcTemplate.update(sql, r.getId(), tags.get(0));
                tags.remove(0);
            }
        }
        catch (Exception e){LOGGER.info(e.getMessage());}
    }
/*    @Override
    public void addTagsToRecipe(List<Tag> tags, Recipe r) {
        while(tags.size()>0){
            String sql = "INSERT INTO RECIPE_TAGS (REPID,TAGID) VALUES(?,?)";
            jdbcTemplate.update(sql,tags.get(0).getId(),r.getId());
            tags.remove(0);
        }
    }*/

    @Override
    public Recipe getRecipeById(Long id) throws NotFoundException {
        LOGGER.trace("getAllRecipe with id "+id);
        final String sql = "select recipe.name, recipe.id, recipe.description,recipe.ingredients,"+
             " array_agg(tagid) FROM RECIPETAGS RIGHT JOIN RECIPE ON REPID=RECIPE.ID WHERE ID = ?" +
       " GROUP BY RECIPE.NAME,RECIPE.ID,RECIPE.DESCRIPTION,RECIPE.INGREDIENTS";
        List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow,id);
        if (r.isEmpty()) throw new NotFoundException("Could not find recipe with id " + id);

        for (Recipe rec: r){
            try {
            LOGGER.info(String.valueOf(rec.returnFirstTag()));
            r.get(0).addTagId(rec.returnFirstTag());}
            catch (Exception e){

            }
        }
        getInstructions(r.get(0));
       // LOGGER.info( r.get(0).toString());
         try{
             String imageData =getImage(r.get(0).getId() );
             r.get(0).setImageData(imageData);
         }
          catch (NotFoundException e){
             LOGGER.info(e.getMessage());
          }

        return r.get(0);
    }

    @Override
    public void deleteRecipe(Recipe r) {
        LOGGER.trace("deleteRecipe({})", r.getName());
        final String sql= "DELETE FROM RECIPE WHERE ID = ?";
        jdbcTemplate.update(sql,r.getId());
    }
    @Override
    public void deleteRecipeById(Long id) {
        LOGGER.trace("deleteRecipe({})",id);
        final String sql= "DELETE FROM RECIPE WHERE ID = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void addImage(Long repid, String fileContent) {
        LOGGER.trace("deleteRecipeImage({})",repid);
        final String sql="delete from image where repid=?";
        jdbcTemplate.update(sql,repid);
        LOGGER.trace("addRecipeImage({})",repid);
        final String sql2="insert into image (repid,blob) values(?,?)";
        jdbcTemplate.update(sql2,repid,fileContent);
    }

    @Override
    public String getImage(Long repid) throws NotFoundException {
        String sql="  select blob from image where repid=?";
        List<String> images= jdbcTemplate.query(sql,this::mapImage,repid);
        if(!(images.size() >0)){
            throw new NotFoundException("Image with given Id doesnt exist");
        }
        return images.get(0);
    }

    @Override
    public List<Recipe> getMostFittingRecipes(String[] tags) {
        //ugly hack needs to be written with prepared statment
         String sql="  select distinct recipe.name,recipe.id,recipe.description,recipe.ingredients from recipe INNER JOIN recipetags" +
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
        List<Recipe> r=  jdbcTemplate.query(sql,this::mapRow);

         return  r;
    }



    private Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
        final Recipe r = new Recipe();
        r.setName(resultSet.getString("name"));
        r.setDescription(resultSet.getString("description"));
       //  r.setInstructions(resultSet.getString("instructions"));
        r.setIngredients(resultSet.getString("ingredients"));
        r.setId(resultSet.getLong("id"));
        try {
            Array a = resultSet.getArray("array_agg");

            Integer[] tagarray = (Integer[]) a.getArray();

            r.setTags((Arrays.asList(tagarray)));
        }
        catch (Exception e){
            return r;
        }
        return r;
    }
    private String mapImage(ResultSet resultSet,int i) throws SQLException{
        return  resultSet.getString("blob");
    }
    private Instruction mapInstruction(ResultSet resultSet, int i) throws SQLException {
        final Instruction instruction= new Instruction();
        instruction.setInstruction(resultSet.getString("instruction"));

        return instruction;
    }
    private  int mapIds(ResultSet resultSet,int i) throws SQLException{
      return resultSet.getInt("id");
    }
}
