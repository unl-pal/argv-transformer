package com.company.util.querygenerator.select;

public class QueryConstants {

	public static final String BEGIN = "SELECT * FROM products JOIN brands ON products.brand_id=brands.id JOIN types ON products.type_id=types.id";
	public static final String END = ";";
	public static final String SPACE = " ";
	public static final String AND = "AND";
	public static final String OR = "OR";
}
