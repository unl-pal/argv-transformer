package com.medallia.w2v4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.medallia.w2v4j.WordVector.Code;
import com.medallia.w2v4j.iterator.IteratorUtils;
import com.medallia.w2v4j.tokenizer.RegexTokenizer;
import com.medallia.w2v4j.tokenizer.Tokenizer;
import com.medallia.w2v4j.utils.MathUtils;

/**
 * {@link Word2VecTrainer} defines the trainer for word2vec model.
 * <p>
 * Instances of {@link Word2VecTrainer} should be created using the {@link {@link Word2VecTrainerBuilder}
 * and can be re-used to train multiple models
 */
public class Word2VecTrainer {
	private static final Logger logger = LogManager.getLogger(); 
	
	/** Only log a debug message every 10,000 sentences */
	private static final int DEBUG_THROTTLE_FACTOR = 10_000;
	
	private static final double MIN_ALPHA = 0.0001; 				// don't allow learning rate to drop below this threshold
	private static final int BATCH_SIZE = 10_000;					// number of sentences per batch for multi-threaded processing
	private static final String OOV_WORD = "<OOV>"; 				// constant for out-of-vocabulary words
	
	private final int numWorker;							// number of threads for training
	private final int window;								// window size 
	private final int layerSize;							// dimension of word vector
	private final int minCount; 							// the minimum frequency that a word in vocabulary needs to satisfy
	private final double initAlpha;							// initial learning rate
	private final NeuralNetworkLanguageModel model;			// neural network model
	private final boolean sampling;							// enable sub-sampling for frequent words
	private final double samplingThreshold; 				// only useful when sampling is turned on
	
	private final Tokenizer tokenizer;						// tokenizer to split sentences into words
	
	/** Builder for {@link Word2VecTrainer} */
	public static class Word2VecTrainerBuilder {
		private int window = 5;
		private int layerSize = 100;
		private int minCount = 5;
		private double initAlpha = 0.025;
		private int numWorker = Runtime.getRuntime().availableProcessors();
		private boolean sampling = false;
		private NeuralNetworkLanguageModel model = NeuralNetworkLanguageModel.SKIP_GRAM;
		private double samplingThreshold = 1e-5;
		
		private Tokenizer tokenizer;
	}

	/**
	 * Wrapper class for thread-safe variables that need to be synchronized among workers on different threads
	 * and constants that are needed for training
	 */ 
	static class TrainingContext {
		// Fixed parameters for training
		private final long totalWordCount;
		private final ImmutableMap<String, WordVector> vocab;
		private final int window;
		private final int layerSize;
		
		// These will be updated by the workers
		private final AtomicLong wordCount = new AtomicLong(0);			// current word count
		private final AtomicLong sentenceCount = new AtomicLong(0);		// current sentence count
		private volatile double alpha;									// learning rate, decreasing as training proceeds
	}

	/**
	 * HuffmanTree for creating tree structure using Hierarchical Softmax
	 */
	private class HuffmanTree {
		private final ImmutableMap<String, WordVector> vocab;
		private final long wordCount;
		
		
	}
	
	/** Node of a Huffman tree */
	private static class HuffmanNode implements Comparable<HuffmanNode> {
		private final String word; 
		private final long count;
		HuffmanNode left;
		HuffmanNode right;
	}
	
	/** Type of neural network language model */
	public enum NeuralNetworkLanguageModel {
		/** Skip-Gram Model */
		SKIP_GRAM {
			},
		/** Continuous Bag of Words */
		CBOW {
			}
	}

	/**
	 * {@link Word2VecWorker} represent workers that can be concurrently executed to train the word2vec model 
	 * using asynchronous stochastic gradient descent
	 */
	private class Word2VecWorker implements Runnable {
		private final TrainingContext context;
		private final Iterable<String> sentences;
	}
}
