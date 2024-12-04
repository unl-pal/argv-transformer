package org.grouplens.lenskit.graphchi.util.matrixmarket;

import org.grouplens.lenskit.data.dao.EventCollectionDAO;
import org.grouplens.lenskit.data.event.Rating;
import org.grouplens.lenskit.data.event.SimpleRating;
import org.grouplens.lenskit.data.pref.IndexedPreference;
import org.grouplens.lenskit.data.snapshot.PackedPreferenceSnapshot;
import org.grouplens.lenskit.data.snapshot.PreferenceSnapshot;
import org.grouplens.lenskit.graphchi.util.MatrixEntry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestPreferenceSnapshotMatrixSource {
    PreferenceSnapshot snapshot;
    PreferenceSnapshotMatrixSource source;

    private static int eid;
    private int rows;
    private int columns;
    private int entries;

    private static double EPSILON = 1e-6;

}
