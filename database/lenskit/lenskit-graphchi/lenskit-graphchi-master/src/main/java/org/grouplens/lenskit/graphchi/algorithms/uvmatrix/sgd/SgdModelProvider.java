/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2012 Regents of the University of Minnesota and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.grouplens.lenskit.graphchi.algorithms.uvmatrix.sgd;

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
import org.grouplens.lenskit.transform.clamp.ClampingFunction;
import org.grouplens.lenskit.iterative.params.LearningRate;
import org.grouplens.lenskit.iterative.params.RegularizationTerm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

/**
 * SGD recommender builder. It uses GraphChi's SGD algorithm to compute the factorized matrices.
 * These are documented at <a href="https://code.google.com/p/graphchi/">its code.google page</a>
 *
 * @author Daniel Gratzer < danny.gratzer@gmail.com >
 */
public class SgdModelProvider extends GraphchiProvider<SgdModel>  {

    private static Logger logger = LoggerFactory.getLogger(SgdModelProvider.class);
    private int featureCount;
    private double lambda;
    private double gamma;
    private ClampingFunction clamp;
    private PreferenceDomain domain;
    private BaselinePredictor baseline;
}
