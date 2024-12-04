package com.company.entity;

public enum Payment implements Entity{
	
	CASH("Cash"), 
	CARD("Card");
	
	private String name;
	
	Payment(String name){
		this.name = name;
	}
	
	public static String getNameByKey(int key){
		return Payment.values()[key-1].name;
	}	
	
	public static int getKeyByName(String name){
		for(int i=0; i<Payment.values().length; i++){
			if(Payment.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return -1;
	}
}