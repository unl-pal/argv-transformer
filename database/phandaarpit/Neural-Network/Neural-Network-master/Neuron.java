import graph.generator.GenerateGraph;

import java.util.ArrayList;

public class Neuron {
	
	private final double learningRate = 0.15;
	private final double momentum = 0.9;
	
	private double output;
	private double input;
	private int indexInLayer;
	private double gradient;

	private ArrayList<Double> weightsForOutputs;
	private ArrayList<Double> changeInWeights;

	private double error;
	
}
