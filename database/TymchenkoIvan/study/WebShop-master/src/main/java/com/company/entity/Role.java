package com.company.entity;

public enum Role {
	
	USER("User"), 
	ADMIN("Admin"), 
	SUPER_ADMIN("Super admin");
	
	private String name;
	
	Role(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public static String getNameByKey(int key){
		return Role.values()[key-1].name;
	}	
	
	public static int getKeyByName(String name){
		
		for(int i=0; i<Role.values().length; i++){
			if(Role.values()[i].name.equalsIgnoreCase(name)){
				return i+1;
			}
		}
		
		return 0;
	}
}
