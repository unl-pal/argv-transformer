package org.grouplens.lenskit.graphchi.algorithms.uvmatrix.sgd;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.grouplens.lenskit.*;
import org.grouplens.lenskit.core.LenskitRecommenderEngineFactory;
import org.grouplens.lenskit.data.dao.EventCollectionDAO;
import org.grouplens.lenskit.data.event.Rating;
import org.grouplens.lenskit.data.event.Ratings;
import org.grouplens.lenskit.graphchi.algorithms.param.FeatureCount;
import org.grouplens.lenskit.graphchi.util.matrixmarket.PreferenceSnapshotMatrixSource;
import org.grouplens.lenskit.graphchi.util.matrixmarket.UserItemMatrixSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Ignore("Currently this is using FunkSVD's unit tests which are unstable")
public class TestSgdRecommender {
    private static Recommender sgdRecommender;
    private static ItemRecommender recommender;
}