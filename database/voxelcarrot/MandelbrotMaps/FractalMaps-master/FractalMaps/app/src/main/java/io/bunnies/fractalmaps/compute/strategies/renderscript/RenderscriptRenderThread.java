package io.bunnies.fractalmaps.compute.strategies.renderscript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bunnies.fractalmaps.compute.FractalComputeArguments;

public class RenderscriptRenderThread extends Thread {
    private final Logger LOGGER = LoggerFactory.getLogger(RenderscriptRenderThread.class);

    private RenderscriptFractalComputeStrategy strategy;
    private final Boolean readyLock = false;
    private boolean waitingForRender = false;
    private volatile boolean isStopped = false;
}