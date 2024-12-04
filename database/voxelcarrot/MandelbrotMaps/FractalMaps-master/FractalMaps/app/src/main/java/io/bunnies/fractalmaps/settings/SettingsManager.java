package io.bunnies.fractalmaps.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bunnies.fractalmaps.IFractalSceneDelegate;
import io.bunnies.fractalmaps.R;
import io.bunnies.fractalmaps.colouring.EnumColourStrategy;
import io.bunnies.fractalmaps.overlay.pin.PinColour;
import io.bunnies.fractalmaps.settings.saved_state.SavedGraphArea;
import io.bunnies.fractalmaps.settings.saved_state.SavedJuliaGraph;

public class SettingsManager implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);

    public static final double DEFAULT_DETAIL_LEVEL = 15;
    private Context context;
    private IFractalSceneDelegate sceneDelegate;

    private static final String PREFERENCE_KEY_CRUDE_FIRST = "CRUDE";
    private static final boolean PREFERENCE_CRUDE_FIRST_DEFAULT = true;

    private static final String PREFERENCE_KEY_SHOW_TIMES = "SHOW_TIMES";
    private static final boolean PREFERENCE_SHOW_TIMES_DEFAULT = false;

    private static final String PREFERENCE_KEY_PIN_COLOUR = "PIN_COLOUR";
    private static final String PREFERENCE_PIN_COLOUR_DEFAULT = "blue";

    private static final String PREFERENCE_KEY_MANDELBROT_COLOUR = "MANDELBROT_COLOURS_V4";
    private static final EnumColourStrategy PREFERENCE_MANDELBROT_COLOUR_DEFAULT = EnumColourStrategy.PURPLE_RED;

    private static final String PREFERENCE_KEY_JULIA_COLOUR = "JULIA_COLOURS_V4";
    private static final EnumColourStrategy PREFERENCE_JULIA_COLOUR_DEFAULT = EnumColourStrategy.PURPLE_YELLOW;

    public static final String PREVIOUS_MAIN_GRAPH_AREA = "prevMainGraphArea";
    public static final String PREVIOUS_LITTLE_GRAPH_AREA = "prevLittleGraphArea";
    public static final String PREVIOUS_JULIA_PARAMS = "prevJuliaParams";
    public final String PREVIOUS_JULIA_GRAPH = "prevJuliaGraph";

    public static final String PREFERENCE_KEY_MANDELBROT_DETAIL = "MANDELBROT_DETAIL";
    public static final String PREFERENCE_KEY_JULIA_DETAIL = "JULIA_DETAIL";
    public static final String DETAIL_CHANGED_KEY = "DETAIL_CHANGED";

    public static final String PREFERENCE_KEY_FIRST_TIME = "FirstTime";

    public static final String PREFERENCE_KEY_LAYOUT_TYPE = "LAYOUT_TYPE";
    public SceneLayoutEnum defaultLayoutType = SceneLayoutEnum.SIDE_BY_SIDE;

    public static final String PREFERENCE_KEY_VIEWS_SWITCHED = "VIEWS_SWITCHED";
    public boolean defaultViewsSwitched = false;
}
