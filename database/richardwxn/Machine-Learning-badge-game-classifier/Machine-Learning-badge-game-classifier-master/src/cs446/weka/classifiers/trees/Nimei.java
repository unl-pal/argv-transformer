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

public class Nimei {

   static String[] features;
   private static FastVector zeroOne;
   private static FastVector labels;

   static {
	features = new String[] { "firstName0", "firstName1", "firstName2", "firstName3", "firstName4", "lastName0", "lastName1", "lastName2", "lastName3", "lastName4" };

	List<String> ff = new ArrayList<String>();

	for (String f : features) {
	    for (char letter = 'a'; letter <= 'z'; letter++) {
		ff.add(f + "=" + letter);
	    }
	ff.add(f + "=*");
	}

	features = ff.toArray(new String[ff.size()]);

	zeroOne = new FastVector(2);
	zeroOne.addElement("1");
	zeroOne.addElement("0");

	labels = new FastVector(2);
	labels.addElement("+");
	labels.addElement("-");
   }
}

