package io.bunnies.fractalmaps.compute.strategies.renderscript;

import android.content.Context;
import androidx.renderscript.*;
import android.util.SparseArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import io.bunnies.fractalmaps.R;
import io.bunnies.fractalmaps.compute.FractalComputeArguments;
import io.bunnies.fractalmaps.compute.IFractalComputeDelegate;
import io.bunnies.fractalmaps.compute.strategies.FractalComputeStrategy;
import io.bunnies.fractalmaps.presenter.FractalPresenter;

public abstract class RenderscriptFractalComputeStrategy extends FractalComputeStrategy {
    final Logger LOGGER = LoggerFactory.getLogger(RenderscriptFractalComputeStrategy.class);

    private RenderScript renderScript;
    protected ScriptC_mandelbrot fractalRenderScript;
    private Allocation pixelBufferAllocation;
    private Allocation pixelBufferSizesAllocation;
    private Context context;

    private LinkedBlockingQueue<FractalComputeArguments> renderQueueList = new LinkedBlockingQueue<FractalComputeArguments>();
    private RenderscriptRenderThread renderThreadList;
    private Boolean rendersComplete;

    private Allocation row_indices_alloc;
    public SparseArray<SparseArray<int[][]>> rowIndices;

    private static final int MIN_LINES_PER_PROGRESS_UPDATE = 32;
}
