package com.movie.worth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.movie.worth.util.Movie;

public class Movies extends JdbcDaoSupport implements MovieDAO {
	
	private static final String[] GENRES = {"unknown", "Action", "Adventure", "Animation", "Children_s",
			"Comedy", "Crime", "Documentary", "Drama", "Fantasy", "Film_Noir", "Horror", "Musical", 
			"Mystery", "Romance", "Sci_Fi", "Thriller", "War", "Western"};
}
