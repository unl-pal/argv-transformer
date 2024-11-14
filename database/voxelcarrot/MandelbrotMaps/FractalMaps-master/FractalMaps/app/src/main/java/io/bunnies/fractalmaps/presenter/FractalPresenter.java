package io.bunnies.fractalmaps.presenter;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import io.bunnies.fractalmaps.IFractalSceneDelegate;
import io.bunnies.fractalmaps.compute.FractalComputeArguments;
import io.bunnies.fractalmaps.compute.IFractalComputeDelegate;
import io.bunnies.fractalmaps.compute.strategies.IFractalComputeStrategy;
import io.bunnies.fractalmaps.overlay.IFractalOverlay;
import io.bunnies.fractalmaps.overlay.label.LabelOverlay;
import io.bunnies.fractalmaps.touch.IFractalTouchDelegate;
import io.bunnies.fractalmaps.touch.IFractalTouchHandler;
import io.bunnies.fractalmaps.view.IFractalView;
import io.bunnies.fractalmaps.view.IViewResizeListener;

public class FractalPresenter implements IFractalPresenter, IFractalComputeDelegate, IFractalTouchDelegate, IViewResizeListener {
    private final Logger LOGGER = LoggerFactory.getLogger(FractalPresenter.class);

    public IFractalComputeStrategy fractalStrategy;
    public IFractalView view;
    public IFractalTouchHandler touchHandler;
    private IFractalSceneDelegate sceneDelegate;

    private Context context;

    private Matrix transformMatrix;

    private int[] pixelBuffer;
    private int[] pixelBufferSizes;
    private double[] graphArea;

    private int viewWidth;
    private int viewHeight;

    protected double detail;

    private final int ZOOM_SLIDER_SCALING = 300;
    private double MINZOOM_LN_PIXEL = -3;

    // Touch
    boolean hasZoomed;

    // Default pixel block sizes for crude, detailed renders
    public static final int CRUDE_PIXEL_BLOCK = 3;
    public static final int DEFAULT_PIXEL_SIZE = 1;

    // How many iterations, at the very fewest, will we do?
    protected int MIN_ITERATIONS = 10;

    // Constants for iteration number calculations
    protected static final double LITTLE_DETAIL_BOOST = 1.5; //Need to bump up the scaling on the little view so it looks better.
    protected static final double DETAIL_DIVISOR = 50;

    // Overlays
    private List<IFractalOverlay> fractalPresenterOverlays;
    private LabelOverlay coordinatesOverlay;

    private double[] lastSaneGraphArea;

    @Override
    public void recomputeGraph(int pixelBlockSize) {
        LOGGER.debug("Starting new style render");

        double[] graphArea = this.getGraphArea();

        String coordinates = "Coordinates " + graphArea[0] + " " + graphArea[1] + " " + graphArea[2];
        this.coordinatesOverlay.setText(coordinates);
        LOGGER.info("Computing: " + coordinates);

        // Empirically determined lines per update
        double absLnPixelSize = Math.abs(Math.log(getPixelSize(this.viewWidth, this.graphArea)));
        double adjustedLog = ((absLnPixelSize - 9.0D) / 1.3D);
        if (adjustedLog < 2.0D)
            adjustedLog = 2.0D;

        if (adjustedLog > 32.0D)
            adjustedLog = 32.0D;

        LOGGER.info("Adjusted log: {}", adjustedLog);

        int nextPowerOfTwo = FractalPresenter.nextClosestPowerOfTwo((int) Math.ceil(adjustedLog));

        LOGGER.info("Zoom power of two: " + nextPowerOfTwo);
        int linesPerUpdate = this.viewHeight / nextPowerOfTwo;
        LOGGER.info("Notifying of update every " + linesPerUpdate + " lines");

        if (pixelBlockSize == DEFAULT_PIXEL_SIZE)
            this.sceneDelegate.setRenderingStatus(this, true);

        this.fractalStrategy.computeFractal(new FractalComputeArguments(pixelBlockSize,
                this.getMaxIterations(),
                linesPerUpdate,
                DEFAULT_PIXEL_SIZE,
                this.viewWidth,
                this.viewHeight,
                graphArea[0],
                graphArea[1],
                getPixelSize(this.viewWidth, this.graphArea),
                this.pixelBuffer,
                this.pixelBufferSizes));

        this.sceneDelegate.onFractalRecomputeScheduled(this);
    }

    public void stopDraggingFractal(boolean stoppedOnZoom, float totalDragX, float totalDragY) {
        LOGGER.debug("Stopped dragging: {} {}", totalDragX, totalDragY);

        if (totalDragX < -this.viewWidth)
            totalDragX = -this.viewWidth;

        if (totalDragX > this.viewWidth)
            totalDragX = this.viewWidth;

        if (totalDragY < -this.viewHeight)
            totalDragY = -this.viewHeight;

        if (totalDragY > this.viewHeight)
            totalDragY = this.viewHeight;

        if (!hasZoomed && !stoppedOnZoom) {
            this.translatePixelBuffer((int) totalDragX, (int) totalDragY);
            this.view.setBitmapPixels(this.pixelBuffer);
        }

        this.translateGraphArea((int) totalDragX, (int) totalDragY);

        if (!stoppedOnZoom) {
            this.setGraphArea(graphArea);
            this.sceneDelegate.scheduleRecomputeBasedOnPreferences(this, false);
        }

        if (!hasZoomed && !stoppedOnZoom) {
            this.transformMatrix.reset();
            this.view.setFractalTransformMatrix(this.transformMatrix);
        }

        this.hasZoomed = false;
        this.view.postUIThreadRedraw();
    }
}
