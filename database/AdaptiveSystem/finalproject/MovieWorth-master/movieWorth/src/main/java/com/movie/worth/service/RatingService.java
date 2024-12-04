package com.movie.worth.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.worth.dao.Ratings;
import com.movie.worth.dao.Users;
import com.movie.worth.util.Rating;
import com.movie.worth.util.User;

@Service
public class RatingService {

	@Autowired
	private Ratings ratings;
	@Autowired
	private Users users;
}
