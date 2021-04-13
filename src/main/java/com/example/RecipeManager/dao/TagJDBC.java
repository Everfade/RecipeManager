package com.example.RecipeManager.dao;

import com.example.RecipeManager.model.Recipe;
import com.example.RecipeManager.model.Tag;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TagJDBC implements TagDao {
    private static final String TABLE_NAME = "tag";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    public TagJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addTag(Tag r) {

    }

    @Override
    public void deleteTag(Tag r) {

    }

    @Override
    public void editTag(Tag r) {

    }

    @Override
    public List<Tag> getAllTags() throws NotFoundException {
        LOGGER.trace("getAllTags");
        final String sql = "SELECT * FROM " + TABLE_NAME ;
        List<Tag> tags = jdbcTemplate.query(sql, this::mapRow);
        if (tags.isEmpty()) throw new NotFoundException("No Tags in Database");
        return tags;
    }
    private Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        final Tag r = new Tag();
        r.setName(resultSet.getString("name"));
        r.setColor(resultSet.getString("color"));
        r.setId(resultSet.getLong("id"));
        return r;
    }

}

