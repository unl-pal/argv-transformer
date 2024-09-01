/** filtered and transformed by PAClab */
package assignment9;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Class to test various methods of the priority queues
 */
public class PriorityQueueTester {
	/** PACLab: suitable */
	 public void testBulkHEAP() {
		int operations = Verifier.nondetInt();
		int nextRandomInt = Verifier.nondetInt();

		// for the first 50% of the operations just add things to the PQs to populate them
		for (int i = 0; i < 0.50 * operations; i++) {
		}

		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = Verifier.nondetInt();

			// first type of random operation is add()
			if (Verifier.nondetInt() == 0) {
			}
			// second type of random operation is findMin()
			else if (Verifier.nondetInt() == 1) {
			} else {
			}
		}
	}

	/** PACLab: suitable */
	 public void testBulkBST() {
		int operations = Verifier.nondetInt();
		int nextRandomInt;

		// for the first 50% of the operations just add things to the PQs to populate them
		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = Verifier.nondetInt();
			// don't repeat items to maintain PriorityHeap set properties
			if (Verifier.nondetInt() == 0 || !jpq.contains(nextRandomInt)) {
			}
		}

		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = Verifier.nondetInt();

			// first type of random operation is add()
			if (Verifier.nondetInt() == 0) {

				// don't repeat items to maintain PriorityHeap set properties
				if (Verifier.nondetInt() != 0 && jpq.contains(nextRandomInt))
					continue;
			}

			// second type of random operation is findMin()
			else if (Verifier.nondetInt() == 1) {
			} else {
			}
		}
	}
}
