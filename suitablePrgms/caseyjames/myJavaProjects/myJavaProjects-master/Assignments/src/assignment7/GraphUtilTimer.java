package assignment7;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class GraphUtilTimer {
    void DFStimer() {
        int timesToLoop = 100;  // higher number causes more accurate average time
        int maxSize = 10000;   // determines right boundary of plot
        Random rand = new Random(656794684984L); // used to create random lists
        List<String> names;
        String name1, name2;
        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavg");

        // testing loop
        for (int i = 0; i <= maxSize; i += 1000) {
            //this allows to have the first data be 100, but then go in increments of 1000 and ending at 10000
            if (i == 0) i = 100;
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                // create the dot file with specified parameters
                GraphUtil.generateGraphInDotFile("testGraph.dot", i, 3, false, true, false);
                // create the graph from the dot file
                Graph testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                // hashmap to verify key names are in the graph
                HashMap<String,Vertex> keys = testGraph.getVertices();
                for (int k = 0; k < 5; k++) {
                    // rebuild the graph each loop
                    testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                    name1 = "v";
                    name2 = "v";
                    // though same graph use different random key names
                    while (! keys.containsKey(name1))
                        name1 = "v" + (i - rand.nextInt(i));
                    while (! keys.containsKey(name2))
                        name2 = "v" + (i - rand.nextInt(i));
                    names = GraphUtil.depthFirstSearch(testGraph, name1, name2);
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                GraphUtil.generateGraphInDotFile("testGraph.dot", i, 3, false, true, false);
                Graph testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                HashMap<String,Vertex> keys = testGraph.getVertices();
                for (int k = 0; k < 5; k++) {
                    name1 = "v";
                    name2 = "v";
                    while (! keys.containsKey(name1))
                        name1 = "v" + (i - rand.nextInt(i));
                    while (! keys.containsKey(name2))
                        name2 = "v" + (i - rand.nextInt(i));
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            // this is the avg time per use of breadthFirstSearch call
            double avgTime = totalTime / 5;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 100) i = 0;
        }
    }

    void BFStimer() {
        int timesToLoop = 100;  // higher number causes more accurate average time
        int maxSize = 10000;   // determines right boundary of plot
        Random rand = new Random(656794684984L); // used to create random lists
        List<String> names;
        String name1, name2;
        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavg");

        // testing loop
        for (int i = 0; i <= maxSize; i += 1000) {
            //this allows to have the first data be 100, but then go in increments of 1000 and ending at 10000
            if (i == 0) i = 100;
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                // create the dot file with specified parameters
                GraphUtil.generateGraphInDotFile("testGraph.dot", i, 2, false, true, false);
                // create the graph from the dot file
                Graph testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                // hashmap to verify key names are in the graph
                HashMap<String,Vertex> keys = testGraph.getVertices();
                for (int k = 0; k < 5; k++) {
                    // rebuild the graph each loop
                    testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                    name1 = "v";
                    name2 = "v";
                    // though same graph use different random key names
                    while (! keys.containsKey(name1))
                        name1 = "v" + (i - rand.nextInt(i));
                    while (! keys.containsKey(name2))
                        name2 = "v" + (i - rand.nextInt(i));
                    names = GraphUtil.breadthFirstSearch(testGraph, name1, name2);
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                GraphUtil.generateGraphInDotFile("testGraph.dot", i, 2, false, true, false);
                Graph testGraph = GraphUtil.buildGraphFromDotFile("testGraph.dot");
                HashMap<String,Vertex> keys = testGraph.getVertices();
                for (int k = 0; k < 5; k++) {
                    name1 = "v";
                    name2 = "v";
                    while (! keys.containsKey(name1))
                        name1 = "v" + (i - rand.nextInt(i));
                    while (! keys.containsKey(name2))
                        name2 = "v" + (i - rand.nextInt(i));
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            // this is the avg time per use of breadthFirstSearch call
            double avgTime = totalTime / 5;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 100) i = 0;
        }
    }
}

