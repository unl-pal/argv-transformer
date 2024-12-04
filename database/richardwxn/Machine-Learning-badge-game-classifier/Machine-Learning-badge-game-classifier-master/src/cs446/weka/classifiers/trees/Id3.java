// CS 446: You shouldn't need to modify this file


package cs446.weka.classifiers.trees;

import weka.classifiers.Classifier;
import weka.classifiers.Sourcable;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.NoSupportForMissingValuesException;
import weka.core.RevisionUtils;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformationHandler;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;

import java.util.Enumeration;
import java.util.Random;

/**
 * <!-- globalinfo-start --> Class for constructing an unpruned decision tree
 * based on the ID3 algorithm. Can only deal with nominal attributes. No missing
 * values allowed. Empty leaves may result in unclassified instances. For more
 * information see: <br/>
 * <br/>
 * R. Quinlan (1986). Induction of decision trees. Machine Learning.
 * 1(1):81-106.
 * <p/>
 * <!-- globalinfo-end -->
 * 
 * <!-- technical-bibtex-start --> BibTeX:
 * 
 * <pre>
 * &#64;article{Quinlan1986,
 *    author = {R. Quinlan},
 *    journal = {Machine Learning},
 *    number = {1},
 *    pages = {81-106},
 *    title = {Induction of decision trees},
 *    volume = {1},
 *    year = {1986}
 * }
 * </pre>
 * <p/>
 * <!-- technical-bibtex-end -->
 * 
 * <!-- options-start --> Valid options are:
 * <p/>
 * 
 * <pre>
 * -D
 *  If set, classifier is run in debug mode and
 *  may output additional info to the console
 * </pre>
 * 
 * <!-- options-end -->
 * 
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 6404 $
 */
public class Id3 extends Classifier implements TechnicalInformationHandler,
	Sourcable {

    /** for serialization */
    static final long serialVersionUID = -2693678647096322561L;

    /** The node's successors. */
    private Id3[] m_Successors;

    /** Attribute used for splitting. */
    private Attribute m_Attribute;

    /** Class value if node is leaf. */
    private double m_ClassValue;

    /** Class distribution if node is leaf. */
    private double[] m_Distribution;

    /** Class attribute of dataset. */
    private Attribute m_ClassAttribute;

    private int maxDepth = -1;

    /**
     * Outputs a tree at a certain level.
     * 
     * @param level
     *            the level at which the tree is to be printed
     * @return the tree as string at the given level
     */
    private String toString(int level) {

	StringBuffer text = new StringBuffer();

	if (m_Attribute == null) {
	    if (Instance.isMissingValue(m_ClassValue)) {
		// text.append(": null");
	    } else {
		text.append(": " + m_ClassAttribute.value((int) m_ClassValue));
	    }
	} else {
	    for (int j = 0; j < m_Attribute.numValues(); j++) {
		if (Instance.isMissingValue(m_Successors[j].m_ClassValue))
		    continue;
		text.append("\n");

		for (int i = 0; i < level; i++) {
		    text.append("|  ");
		}
		text.append(m_Attribute.name() + " = " + m_Attribute.value(j));
		text.append(m_Successors[j].toString(level + 1));
	    }
	}
	return text.toString();
    }
}
