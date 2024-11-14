package io.bunnies.fractalmaps.compute.strategies;

import io.bunnies.fractalmaps.colouring.EnumColourStrategy;
import io.bunnies.fractalmaps.compute.IFractalComputeDelegate;

public abstract class FractalComputeStrategy implements IFractalComputeStrategy {
    protected int width;
    protected int height;
    protected IFractalComputeDelegate delegate;
    protected EnumColourStrategy colourStrategy;

    // Render calculating variables
    protected double xMin, yMax, pixelSize;
}
