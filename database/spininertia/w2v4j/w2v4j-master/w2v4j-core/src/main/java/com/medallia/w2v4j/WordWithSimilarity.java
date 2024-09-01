package com.medallia.w2v4j;

/**
 * Wrapper for a word and its similarity to some other word 
 */
public class WordWithSimilarity implements Comparable<WordWithSimilarity>{
	private final String word;
	private final double similarity;
}
