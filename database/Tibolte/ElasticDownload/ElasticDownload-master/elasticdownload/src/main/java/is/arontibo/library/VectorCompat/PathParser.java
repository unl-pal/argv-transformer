package is.arontibo.library.VectorCompat;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class PathParser {
    static final String LOGTAG = PathParser.class.getSimpleName();

    private static class ExtractFloatResult {
        // We need to return the position of the next separator and whether the
        // next float starts with a '-'.
        int mEndPosition;
        boolean mEndWithNegSign;
    }

    /**
     * Each PathDataNode represents one command in the "d" attribute of the svg
     * file.
     * An array of PathDataNode can represent the whole "d" attribute.
     */
    public static class PathDataNode {
        private char mType;
        private float[] mParams;
    }
}
