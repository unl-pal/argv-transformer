package org.grouplens.lenskit.graphchi.algorithms;

import org.codehaus.plexus.util.FileUtils;
import org.grouplens.lenskit.core.Transient;
import org.grouplens.lenskit.graphchi.util.GraphchiSerializer;
import org.grouplens.lenskit.graphchi.util.matrixmarket.UserItemMatrixSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

abstract public class GraphchiProvider<T> implements Provider<T> {
    private static Logger logger = LoggerFactory.getLogger("GraphchiProvider");
    private static AtomicInteger globalId = new AtomicInteger(0);

    private String graphchi;
    protected String outputDir;
    protected UserItemMatrixSource input;
}
