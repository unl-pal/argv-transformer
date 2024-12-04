package io.bunnies.fractalmaps.touch;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.bunnies.fractalmaps.overlay.pin.IPinMovementDelegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class MandelbrotTouchHandlerTest {
    private MandelbrotTouchHandler touchHandler;
    private IFractalTouchDelegate touchDelegate;
    private IPinMovementDelegate pinMovementDelegate;
}
