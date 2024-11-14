package com.movie.worth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movie.worth.dao.Ratings;
import com.movie.worth.service.MovieService;
import com.movie.worth.service.RatingService;
import com.movie.worth.util.Movie;
import com.movie.worth.util.MovieUserRel;
import com.movie.worth.util.Rating;

@Controller
public class MovieRating {
	
	private static final int INIT_RATING_NO = 5;
	@Autowired
	private RatingService rs;
	@Autowired
	private MovieService ms;
}
