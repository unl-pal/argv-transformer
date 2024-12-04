package org.grouplens.lenskit.graphchi.util;


import static org.junit.Assert.*;

import  org.junit.*;

import org.grouplens.lenskit.data.dao.EventCollectionDAO;
import org.grouplens.lenskit.data.event.Rating;
import org.grouplens.lenskit.data.event.SimpleRating;
import org.grouplens.lenskit.data.snapshot.PackedPreferenceSnapshot;
import org.grouplens.lenskit.graphchi.util.matrixmarket.PreferenceSnapshotMatrixSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestGraphChiSerializer {
    private BufferedReader outputTester;

    private static int rows;
    private static int columns;
    private static int entries;

    static private String filename = "testserializer.txt.tmp";

    private static int eid;

}
