package com.medallia.w2v4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.medallia.w2v4j.utils.MathUtils;

/**
 * Vector representation for a word along with information about its path in the Huffman Tree
 */
public class WordVector implements Serializable {
	private static final long serialVersionUID = 0L;
	
	enum Code {
		LEFT(0),
		RIGHT(1);
		
		private final int value;
		
		private final double samplingRate;	// sampling probability to sample this word
	final double[] vector;
	final ImmutableList<NodeVector> points;
	final ImmutableList<Code> code;
}
