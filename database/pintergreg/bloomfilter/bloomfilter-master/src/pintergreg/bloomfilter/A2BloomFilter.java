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
package pintergreg.bloomfilter;

import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A2 Bloom Filter consists of two @{link ScalableBloomFilter}. Elements added
 * to the active one and at one time only one of them is active, but both of
 * them is read when an element is searched. Active is changed after the given
 * time and the new active is cleared. In this way an element is surely in the
 * Bloom Filter at least for the specified time.
 *
 * @author Gergő Pintér
 */
public class A2BloomFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    private ScalableBloomFilter[] bloomFilters = new ScalableBloomFilter[2];
    private final int m;
    private final int k;
    private final int ttl;
    private boolean stop = false;
    private AtomicInteger active = new AtomicInteger(0);
    private Thread thread;

    /**
     * Timer Thread class that ages the element according to the given Time To
     * Live value
     */
    private class TimerThread extends Thread implements Serializable {

        private static final long serialVersionUID = 1L;
    };

}
