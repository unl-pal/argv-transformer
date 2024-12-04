package com.movie.worth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.worth.dao.Movies;
import com.movie.worth.dao.Ratings;
import com.movie.worth.dao.Users;
import com.movie.worth.util.AdjustedSimilarity;
import com.movie.worth.util.Movie;
import com.movie.worth.util.MovieUserRel;
import com.movie.worth.util.Rating;
import com.movie.worth.util.SlopeOne;

@Service
public class MovieService {
	
	@Autowired
	private Movies movies;
	@Autowired
	private Ratings ratings;
	@Autowired
	private Users user;
	
	@Autowired
	private AdjustedSimilarity as;
	@Autowired
	private SlopeOne so;
}
