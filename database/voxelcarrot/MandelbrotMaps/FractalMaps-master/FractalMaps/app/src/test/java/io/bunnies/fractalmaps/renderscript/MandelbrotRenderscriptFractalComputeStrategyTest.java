package io.bunnies.fractalmaps.compute.strategies.renderscript;

import android.os.Build;
import android.util.SparseArray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.bunnies.fractalmaps.Constants;
import io.bunnies.fractalmaps.compute.FractalComputeArguments;
import io.bunnies.fractalmaps.compute.IFractalComputeDelegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class MandelbrotRenderscriptFractalComputeStrategyTest {
    private MandelbrotRenderscriptFractalComputeStrategy strategy;
    private IFractalComputeDelegate delegate;

    private static final int VIEW_WIDTH = 100;
    private static final int VIEW_HEIGHT = 100;
}
