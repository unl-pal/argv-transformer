package com.company.entity;

public enum Type implements Entity{
	BACKPACK("Backpack"), 
	TENT("Tent"), 
	SLEEP_BAG("Sleepeng bag");
	
	private String name;
	
	Type(String name){
		this.name = name;
	}
	
	public static String getNameByKey(int key){
		return Type.values()[key-1].name;
	}	
	
	public static int getKeyByName(String name){
		for(int i=0; i<Type.values().length; i++){
			if(Type.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return 0;
	}
}
