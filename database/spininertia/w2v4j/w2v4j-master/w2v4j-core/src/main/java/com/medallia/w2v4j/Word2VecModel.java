package com.medallia.w2v4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.medallia.w2v4j.utils.MathUtils;

/**
 * {@code Word2VecModel} defines a trained Word2Vec model
 */
public class Word2VecModel implements Serializable {
	private static final long serialVersionUID = 0L;
	
	private final ImmutableMap<String, WordVector> vocab;	// map from word to its WordNeuron
}
