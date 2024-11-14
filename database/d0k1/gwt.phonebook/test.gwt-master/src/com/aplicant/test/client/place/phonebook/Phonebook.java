package com.aplicant.test.client.place.phonebook;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Phonebook extends Place {
	private String filter = "";
	
	public static class Tokenizer implements PlaceTokenizer<Phonebook> {
    }
}
