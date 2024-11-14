package io.bunnies.fractalmaps.presenter;

import android.graphics.Matrix;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.bunnies.fractalmaps.Constants;
import io.bunnies.fractalmaps.IFractalSceneDelegate;
import io.bunnies.fractalmaps.compute.FractalComputeArguments;
import io.bunnies.fractalmaps.compute.strategies.IFractalComputeStrategy;
import io.bunnies.fractalmaps.touch.IFractalTouchHandler;
import io.bunnies.fractalmaps.view.IFractalView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class FractalPresenterTest {
    private FractalPresenter presenter;
    private IFractalSceneDelegate sceneDelegate;
    private IFractalComputeStrategy computeStrategy;
    private IFractalTouchHandler touchHandler;
    private IFractalView view;

    private static final int VIEW_WIDTH = 100;
    private static final int VIEW_HEIGHT = 100;

    private int countInstancesOfValue(int[] array, int value) {
        int instances = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                instances++;
            }
        }

        return instances;
    }
}
