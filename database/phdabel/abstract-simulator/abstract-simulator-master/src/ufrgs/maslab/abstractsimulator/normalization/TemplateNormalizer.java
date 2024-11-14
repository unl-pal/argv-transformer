package ufrgs.maslab.abstractsimulator.normalization;

import java.util.ArrayList;
import java.util.List;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;

public class TemplateNormalizer implements Normalizer {
	
	private double lowLimit=0, highLimit=1;
    double[] maxIn, maxOut; // contains max values for in and out columns
    double[] minIn, minOut; // contains min values for in and out columns     	

}
