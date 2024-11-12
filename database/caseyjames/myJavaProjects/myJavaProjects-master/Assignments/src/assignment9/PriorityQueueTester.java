package assignment9;

import junit.framework.TestCase;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * Class to test various methods of the priority queues
 */
public class PriorityQueueTester extends TestCase {
	long seed = 3892477348952745972L;
	int operations = 1000;

    public void testBulkHEAP() {
		// create a new test PriorityQueue and comparison Objects and test random operations upon them
		PriorityQueueHEAP<Integer> pq = new PriorityQueueHEAP<Integer>();
		PriorityQueue<Integer> jpq = new PriorityQueue<Integer>();

		Random rand = new Random(seed);
		int nextRandomInt = rand.nextInt();

		// for the first 50% of the operations just add things to the PQs to populate them
		for (int i = 0; i < 0.50 * operations; i++) {
			pq.add(nextRandomInt);
			jpq.add(nextRandomInt);
		}

		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = rand.nextInt();

			// first type of random operation is add()
			if (nextRandomInt % 3 == 0) {
				pq.add(nextRandomInt);
				jpq.add(nextRandomInt);
			}
			// second type of random operation is findMin()
			else if (nextRandomInt % 3 == 1)
				assertEquals(pq.findMin(), jpq.peek());
			// third type of random operation is delMin()
			else
				assertEquals(pq.deleteMin(), jpq.poll());
		}
	}

	public void testBulkBST() {
		// create a new test PriorityQueue and comparison Objects and test random operations upon them
		PriorityQueueBST<Integer> pq = new PriorityQueueBST<Integer>();
		PriorityQueue<Integer> jpq = new PriorityQueue<Integer>();

		Random rand = new Random(seed);
		int nextRandomInt;

		// for the first 50% of the operations just add things to the PQs to populate them
		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = rand.nextInt();
			// don't repeat items to maintain PriorityHeap set properties
			if (jpq.size() == 0 || !jpq.contains(nextRandomInt)) {
				pq.add(nextRandomInt);
				jpq.add(nextRandomInt);
			}
		}

		for (int i = 0; i < 0.50 * operations; i++) {
			nextRandomInt = rand.nextInt();

			// first type of random operation is add()
			if (nextRandomInt % 3 == 0) {

				// don't repeat items to maintain PriorityHeap set properties
				if (jpq.size() != 0 && jpq.contains(nextRandomInt))
					continue;

				pq.add(nextRandomInt);
				jpq.add(nextRandomInt);
			}

			// second type of random operation is findMin()
			else if (nextRandomInt % 3 == 1)
				assertEquals(pq.findMin(), jpq.peek());
			// third type of random operation is delMin()
			else
				assertEquals(pq.deleteMin(), jpq.poll());
		}
	}
}
