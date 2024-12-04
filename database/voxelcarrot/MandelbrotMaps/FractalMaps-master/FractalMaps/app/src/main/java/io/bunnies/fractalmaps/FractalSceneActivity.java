package io.bunnies.fractalmaps;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.bunnies.fractalmaps.colouring.EnumColourStrategy;
import io.bunnies.fractalmaps.compute.strategies.IFractalComputeStrategy;
import io.bunnies.fractalmaps.compute.strategies.JuliaSeedSettable;
import io.bunnies.fractalmaps.compute.strategies.renderscript.JuliaRenderscriptFractalComputeStrategy;
import io.bunnies.fractalmaps.compute.strategies.renderscript.MandelbrotRenderscriptFractalComputeStrategy;
import io.bunnies.fractalmaps.compute.strategies.renderscript.RenderscriptFractalComputeStrategy;
import io.bunnies.fractalmaps.detail.DetailControlDelegate;
import io.bunnies.fractalmaps.detail.DetailControlDialog;
import io.bunnies.fractalmaps.menu.IFractalMenuDelegate;
import io.bunnies.fractalmaps.menu.ISceneMenuDelegate;
import io.bunnies.fractalmaps.overlay.IFractalOverlay;
import io.bunnies.fractalmaps.overlay.pin.IPinMovementDelegate;
import io.bunnies.fractalmaps.overlay.pin.PinColour;
import io.bunnies.fractalmaps.overlay.pin.PinOverlay;
import io.bunnies.fractalmaps.presenter.FractalPresenter;
import io.bunnies.fractalmaps.presenter.IFractalPresenter;
import io.bunnies.fractalmaps.settings.FractalTypeEnum;
import io.bunnies.fractalmaps.settings.SceneLayoutEnum;
import io.bunnies.fractalmaps.settings.SettingsActivity;
import io.bunnies.fractalmaps.settings.SettingsManager;
import io.bunnies.fractalmaps.settings.saved_state.SavedGraphArea;
import io.bunnies.fractalmaps.settings.saved_state.SavedJuliaGraph;
import io.bunnies.fractalmaps.touch.FractalTouchHandler;
import io.bunnies.fractalmaps.touch.MandelbrotTouchHandler;
import io.bunnies.fractalmaps.view.FractalView;

public class FractalSceneActivity extends AppCompatActivity implements IFractalSceneDelegate, IPinMovementDelegate, DetailControlDelegate, ISceneMenuDelegate, IFractalMenuDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(FractalSceneActivity.class);

    // Layout variables
    Toolbar toolbar;
    TextView toolbarTextProgress;
    FractalView firstFractalView;
    FractalView secondFractalView;

    public static final String FRAGMENT_DETAIL_DIALOG_NAME = "detailControlDialog";
    private Map<IFractalPresenter, Boolean> UIRenderStates = new HashMap<>();
    private SceneLayoutEnum layoutType;

    public long sceneStartTime = 0;
    private static final int BUTTON_SPAM_MINIMUM_MS = 1000;

    // Views
    public FractalView mandelbrotFractalView;
    public FractalView juliaFractalView;

    // Presenters
    public FractalPresenter mandelbrotFractalPresenter;
    public FractalPresenter juliaFractalPresenter;

    // Strategies
    public IFractalComputeStrategy mandelbrotStrategy;
    public IFractalComputeStrategy juliaStrategy;
    public JuliaSeedSettable juliaSetter;

    // Overlays
    private List<IFractalOverlay> sceneOverlays;
    public PinOverlay pinOverlay;

    private float previousPinDragX = 0;
    private float previousPinDragY = 0;

    // Settings
    public SettingsManager settings;
    private boolean showingPinOverlay = true;

    // Context menus
    private float pinContextX = 0;
    private float pinContextY = 0;
    public View viewContext;
    private boolean contextFromTouchHandler = false;
}
