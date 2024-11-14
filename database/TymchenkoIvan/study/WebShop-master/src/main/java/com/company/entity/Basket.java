package com.company.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.company.service.bean.ProductBean;

public class Basket implements Entity{
	
	private Map<ProductBean, Integer> map;
}
