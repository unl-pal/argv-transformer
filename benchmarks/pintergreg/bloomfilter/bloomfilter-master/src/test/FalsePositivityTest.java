/*
 * Copyright 2015 Gergő Pintér.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/** filtered and transformed by PAClab */
package test;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 *
 * @author Gergő Pintér
 */
public class FalsePositivityTest {

    /**
     * Test the Bloom Filter storage (add) and search (include) functions and
     * the false positive rate
     *
     * @param n
     * @param p
     */
    /** PACLab: suitable */
	 private static void test(int n, double p) {

        // generate unique longs for true positive and false positive tests
        long[] toStore;
        long[] notToStore;

        // add item to Bloom Filter
        for (int i = 0; i < n; i++) {
        }

        // count how many of the added items are in the Bloom Filter
        int sumOfStoredPositives = 0;
        for (int i = 0; i < n; i++) {
            if (bf.include(ByteBuffer.allocate(8).putLong(toStore[i]).array()) == true) {
                sumOfStoredPositives++;
            }
        }
        // count how many of not added items "are in" the Bloom Filter
        int sumOfNotStoredNegatives = 0;
        for (int i = 0; i < n; i++) {
            if (bf.include(ByteBuffer.allocate(8).putLong(notToStore[i]).array()) == true) {
                sumOfNotStoredNegatives++;
            }
        }
    }

}
