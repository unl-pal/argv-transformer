package io.bunnies.fractalmaps.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import io.bunnies.fractalmaps.overlay.IFractalOverlay;
import io.bunnies.fractalmaps.touch.IFractalTouchHandler;

public class FractalView extends View implements IFractalView {
    private final Logger LOGGER = LoggerFactory.getLogger(FractalView.class);

    private IViewResizeListener resizeListener;
    private Matrix fractalTransformMatrix;

    private int width;
    private int height;

    private Bitmap fractalBitmap;
    private Paint fractalPaint;

    private List<IFractalOverlay> presenterOverlays;
    private List<IFractalOverlay> sceneOverlays;

    private boolean drawOverlays = true;
}