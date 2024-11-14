package com.movie.worth.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.worth.dao.Ratings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *
 * @author lanzhang_mini
 * 
 */
@Service
public class AdjustedSimilarity {
	
	@Autowired
	private Ratings scCalcDAO;
	
	private static final double SIM_THRESHOLD = 0.2;
	private static final int SIM_NO = 10;
    private int tempAvgRating=0;
    private int tempRating=0;
    private HashMap<Integer, Integer> CurrUser = null;
    private int[] SimUserId=null;

}
