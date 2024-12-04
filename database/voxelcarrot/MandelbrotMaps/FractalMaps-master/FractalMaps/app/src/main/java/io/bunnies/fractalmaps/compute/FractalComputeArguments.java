package io.bunnies.fractalmaps.compute;

public class FractalComputeArguments {
    public final int pixelBlockSize;
    public final int maxIterations;
    public final int linesPerProgressUpdate;
    public final int defaultPixelSize;
    public final int viewWidth;
    public final int viewHeight;
    public final double xMin;
    public final double yMax;
    public final double pixelSize;
    public int[] pixelBuffer;
    public int[] pixelBufferSizes;
    public long startTime;
}
