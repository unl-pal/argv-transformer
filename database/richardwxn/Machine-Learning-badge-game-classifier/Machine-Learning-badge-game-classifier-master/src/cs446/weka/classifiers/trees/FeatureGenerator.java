package cs446.weka.classifiers.trees;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/* CS 446:
 * Edit the makeInstance method (and any other necessary fields) to extract the correct set of features 
 */

public class FeatureGenerator {

    static String[] features;
    private static FastVector zeroOne;
    private static FastVector labels;

    static {
    // Define a list of feature template names
    // We provide two feature templates, corresponding to the first and last letters in the first name
	features = new String[] { "firstName0", "firstName1","firstName2","firstName3","firstName4" ,"lastName0" ,"lastName1", "lastName2","lastName3","lastName4" };
    
    // For each feature template, create a feature name for each letter in the alphabet (a to z)
    // Store these feature names in a temporary list, feat_temp
	int p=0;
	List<String> feat_temp = new ArrayList<String>();
	for (String f : features) {
	    for (char letter = 'a'; letter <= 'z'; letter++) {
		feat_temp.add(f + "=" + letter);
	    }
	    
	    feat_temp.add("+"+p);
	    p++;
	    }

        
 
        
        
    // Replace the list of feature template names with the list of feature names in feat_temp
	features = feat_temp.toArray(new String[feat_temp.size()]);

    // Store binary feature values
	zeroOne = new FastVector(2);
	zeroOne.addElement("1");
	zeroOne.addElement("0");

    // Store class labels
	labels = new FastVector(2);
	labels.addElement("+");
	labels.addElement("-");
    }
}
