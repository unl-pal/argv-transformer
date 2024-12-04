package com.company.entity;

import java.util.Date;

public class Order implements Entity{
	
	private long id;
	private long userId;
	private long paymentId;
	private long deliveryId;
	private long statusId;
	private String statusMessage;
	private Date created;
}
