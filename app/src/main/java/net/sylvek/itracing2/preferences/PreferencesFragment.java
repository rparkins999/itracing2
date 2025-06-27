package net.sylvek.itracing2.preferences;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import net.sylvek.itracing2.BluetoothLEService;
import net.sylvek.itracing2.Preferences;
import net.sylvek.itracing2.R;

/**
 * Created by sylvek on 18/05/2015.
 */
public class PreferencesFragment extends PreferenceFragment {

    private OnPreferencesListener presenter;

    public static PreferencesFragment instance()
    {
        Log.d(BluetoothLEService.TAG, "PreferencesFragment instance()");
        final PreferencesFragment preferencesFragment = new PreferencesFragment();
        Bundle arguments = new Bundle();
        preferencesFragment.setArguments(arguments);
        preferencesFragment.setRetainInstance(true);
        return preferencesFragment;
    }

    public void setForegroundBackground(final boolean checked)
    {
        Log.d(BluetoothLEService.TAG, "PreferencesFragment setForegroundBackground()");
        final CheckBoxPreference preference = (CheckBoxPreference) findPreference(Preferences.FOREGROUND);
        preference.setChecked(checked);
        preference.setEnabled(!checked);
    }
    public void setRebootRestart(final boolean checked)
    {
        Log.d(BluetoothLEService.TAG, "PreferencesFragment setRebootRestart()");
        final CheckBoxPreference preference = (CheckBoxPreference) findPreference(Preferences.RESTART_ON_REBOOT);
        preference.setChecked(checked);
        preference.setEnabled(!checked);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(BluetoothLEService.TAG, "PreferencesFragment onCreate()");
        this.addPreferencesFromResource(R.xml.global_preferences);
        findPreference(Preferences.FOREGROUND).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {
                final boolean checked = ((CheckBoxPreference) preference).isChecked();
                presenter.onForegroundChecked(checked);
                return true;
            }
        });
        findPreference(Preferences.RESTART_ON_REBOOT).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {
                final boolean checked = ((CheckBoxPreference) preference).isChecked();
                presenter.onRebootRestartChecked(checked);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Log.d(BluetoothLEService.TAG, "PreferencesFragment onAttach()");
        if (activity instanceof OnPreferencesListener) {
            this.presenter = (OnPreferencesListener) activity;
        } else {
            throw new ClassCastException("must implement OnPreferencesListener");
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(BluetoothLEService.TAG, "PreferencesFragment onStart()");
        this.presenter.onPreferencesStarted();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(BluetoothLEService.TAG, "PreferencesFragment onStop()");
        this.presenter.onPreferencesStopped();
    }

    public interface OnPreferencesListener {

        void onPreferencesStarted();

        void onPreferencesStopped();

        void onForegroundChecked(boolean checked);
        void onRebootRestartChecked(boolean checked);
    }
}
