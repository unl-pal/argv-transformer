package com.company.entity;

public enum Brand implements Entity{
	DEUTER("Deuter"), 
	TATONKA("Tatonka"), 
	MILLET("Millet");
	
	private String name;
	
	Brand(String name){
		this.name = name;
	}
	
	public static String getNameByKey(int key){
		return Brand.values()[key-1].name;
	}
	
	public static int getKeyByName(String name){
		for(int i=0; i<Brand.values().length; i++){
			if(Brand.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return 0;
	}
}
