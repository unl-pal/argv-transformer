package com.company.entity;

import java.math.BigDecimal;

public class OrderItem implements Entity{

	private long id;
	private long orderId;
	private long productId;
	private long count;
	private BigDecimal price;
}
