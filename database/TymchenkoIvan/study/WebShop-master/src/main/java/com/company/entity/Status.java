package com.company.entity;

public enum Status implements Entity{
	
	CONFIRMED("Confirmed"), 
	FORMED("Formed"), 
	SENT("Sent"), 
	COMPLETED("Completed"), 
	CANCELED("Canceled");
	
	private String name;
	
	Status(String name){
		this.name = name;
	}
	
	public static String getNameByKey(int key){
		return Status.values()[key-1].name;
	}	
	
	public static int getKeyByName(String name){
		for(int i=0; i<Status.values().length; i++){
			if(Status.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return 0;
	}
}