package io.bunnies.fractalmaps.detail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import io.bunnies.fractalmaps.R;
import io.bunnies.fractalmaps.settings.SettingsManager;

public class DetailControlDialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    private DetailControlDelegate delegate;

    Button applyButton;
    Button defaultsButton;
    Button cancelButton;
    SeekBar mandelbrotBar;
    SeekBar juliaBar;
    TextView mandelbrotText;
    TextView juliaText;
}
