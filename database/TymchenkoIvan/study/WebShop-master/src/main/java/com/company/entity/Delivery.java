package com.company.entity;

public enum Delivery implements Entity{
	
	ADDRESS("Address"), 
	STORAGE("Storage"), 
	MAIL("Mail");
	
	private String name;
	
	Delivery(String name){
		this.name = name;
	}
	
	public static String getNameByKey(int key){
		return Delivery.values()[key-1].name;
	}	
	
	public static int getKeyByName(String name){
		for(int i=0; i<Delivery.values().length; i++){
			if(Delivery.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return -1;
	}
}