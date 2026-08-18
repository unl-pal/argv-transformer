package com.example;

/**
 * Not suitable: no typed parameters and no operations on them, so the filter
 * should drop this file before the transformer ever sees it.
 */
public class Greeter {

	public void greet() {
		System.out.println("hello");
	}
}
