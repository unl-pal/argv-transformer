package com.aplicant.test.server.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.aplicant.test.shared.model.Contact;

public class ContactDAO {
	private HashMap<String, Contact> data = new HashMap<String, Contact>();
//	private ArrayList<Contact> data = new ArrayList<Contact>(); 
	
	private static ContactDAO instance = null;
}
