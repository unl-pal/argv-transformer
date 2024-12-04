package com.company.entity;

import java.math.BigDecimal;

public class Product implements Entity{
	
	private long id;
	private long typeId;
	private long brandId;
	private BigDecimal price;
	private String name;
	private String text;
}
