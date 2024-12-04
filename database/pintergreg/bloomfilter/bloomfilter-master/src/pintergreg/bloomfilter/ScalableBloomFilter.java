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
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Implementation of Scalable Bloom Filter that extends its capacity dynamically
 * if the Bloom Filter gets saturated. This extension means creating new Bloom
 * Filters that are linked through a LinkedList.
 *
 * @author Gergő Pintér
 */
public class ScalableBloomFilter implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private LinkedList<ExtendedBloomFilter> bloomFilters = new LinkedList<>();
    ListIterator<ExtendedBloomFilter> listIterator;
    private final int m;
    private final int k;
}
