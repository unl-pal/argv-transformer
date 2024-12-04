package org.grouplens.lenskit.graphchi.algorithms.uvmatrix.als;

import org.grouplens.lenskit.baseline.BaselinePredictor;
import org.grouplens.lenskit.core.Transient;
import org.grouplens.lenskit.data.pref.PreferenceDomain;
import org.grouplens.lenskit.graphchi.algorithms.GraphchiProvider;
import org.grouplens.lenskit.graphchi.algorithms.param.FeatureCount;
import org.grouplens.lenskit.graphchi.util.MatrixEntry;
import org.grouplens.lenskit.graphchi.util.matrices.DenseMatrix;
import org.grouplens.lenskit.graphchi.util.matrixmarket.BufferedReaderMatrixSource;
import org.grouplens.lenskit.graphchi.util.matrixmarket.MatrixSource;
import org.grouplens.lenskit.graphchi.util.matrixmarket.UserItemMatrixSource;
import org.grouplens.lenskit.iterative.params.RegularizationTerm;
import org.grouplens.lenskit.transform.clamp.ClampingFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.IOException;

public class AlsModelProvider extends GraphchiProvider<AlsModel> {
    private static Logger logger = LoggerFactory.getLogger(AlsModelProvider.class);


    private int featureCount;
    private UserItemMatrixSource trainMatrix;
    private double lambda;
    private PreferenceDomain domain;
    private ClampingFunction clamp;
    private BaselinePredictor baseline;

}
