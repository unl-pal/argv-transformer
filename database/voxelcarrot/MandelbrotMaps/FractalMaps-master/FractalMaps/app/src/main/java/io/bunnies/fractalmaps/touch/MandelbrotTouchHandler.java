package io.bunnies.fractalmaps.touch;

import android.content.Context;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bunnies.fractalmaps.overlay.pin.IPinMovementDelegate;

public class MandelbrotTouchHandler extends FractalTouchHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(MandelbrotTouchHandler.class);

    private IPinMovementDelegate delegate;
    private boolean draggingPin;
}
