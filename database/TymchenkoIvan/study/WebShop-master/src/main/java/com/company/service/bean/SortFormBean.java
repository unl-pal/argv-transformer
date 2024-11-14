package com.company.service.bean;

import java.util.List;

/**
 * Bean object contains fields from sort form.
 * 
 * @author Ivan_Tymchenko
 */
public class SortFormBean implements Bean{
	
	private static final long serialVersionUID = 2267055308179934053L;
	
	private String search;
	private String minPrice;
	private String maxPrice;
	private String onPage;
	private String sorting;
	private String pageNumber;
	private List<String> types;
	private List<String> brands;
}
