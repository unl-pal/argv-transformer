package com.company.service.bean;

import java.util.Map;

public class OrderInfoBean implements Bean{
	
	private static final long serialVersionUID = -7803493963234268205L;
	
	private long userId;
	private long deliveryId;
	private long paymentId;
	private Map<ProductBean, Integer> map;
}
