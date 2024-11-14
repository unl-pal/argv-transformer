package io.bunnies.fractalmaps;

import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import io.bunnies.fractalmaps.FractalSceneActivity;
import io.bunnies.fractalmaps.MandelbrotJuliaLocation;
import io.bunnies.fractalmaps.colouring.EnumColourStrategy;
import io.bunnies.fractalmaps.compute.strategies.IFractalComputeStrategy;
import io.bunnies.fractalmaps.overlay.pin.PinColour;
import io.bunnies.fractalmaps.overlay.pin.PinOverlay;
import io.bunnies.fractalmaps.presenter.FractalPresenter;
import io.bunnies.fractalmaps.presenter.IFractalPresenter;
import io.bunnies.fractalmaps.settings.SettingsManager;
import io.bunnies.fractalmaps.settings.saved_state.SavedGraphArea;
import io.bunnies.fractalmaps.settings.saved_state.SavedJuliaGraph;
import io.bunnies.fractalmaps.view.FractalView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class FractalSceneActivityTest {
    private FractalSceneActivity activity;
}