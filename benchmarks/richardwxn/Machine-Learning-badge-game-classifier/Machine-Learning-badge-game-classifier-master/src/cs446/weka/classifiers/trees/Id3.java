// CS 446: You shouldn't need to modify this file


/** filtered and transformed by PAClab */
package cs446.weka.classifiers.trees;

import org.sosy_lab.sv_benchmarks.Verifier;

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
public class Id3 {

    /**
     * Outputs a tree at a certain level.
     * 
     * @param level
     *            the level at which the tree is to be printed
     * @return the tree as string at the given level
     */
    /** PACLab: suitable */
	 private Object toString(int level) {

	boolean m_Attribute = Verifier.nondetBoolean();
	if (m_Attribute == null) {
	    if (Verifier.nondetBoolean()) {
		// text.append(": null");
	    } else {
	    }
	} else {
	    for (int j = 0; j < Verifier.nondetInt(); j++) {
		if (Verifier.nondetBoolean())
		    continue;
		for (int i = 0; i < level; i++) {
		}
	    }
	}
	return new Object();
    }
}
