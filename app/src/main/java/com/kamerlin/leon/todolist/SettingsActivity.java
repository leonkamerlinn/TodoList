package com.kamerlin.leon.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SettingsPreferenceFragment())
                .commit();
    }

    public static class SettingsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);

            findPreference(getString(R.string.pref_key_notifications_ringtone))
                    .setOnPreferenceChangeListener(this);

            Preference preference = findPreference(getString(R.string.pref_key_version));
            preference.setSummary(BuildConfig.VERSION_NAME);

            findPreference(getString(R.string.pref_key_vibrate))
                    .setOnPreferenceChangeListener(this);

            Preference themePref = findPreference(getString(R.string.pref_key_theme));
            themePref.setOnPreferenceChangeListener(this);
            themePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), ThemeActivity.class));
                    return true;
                }
            });


        }

        @Override
        public void onStart() {
            super.onStart();
            Preference themePref = findPreference(getString(R.string.pref_key_theme));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            themePref.setSummary(sharedPreferences.getString(getString(R.string.pref_key_theme), getString(R.string.app_theme)));

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            System.out.println("hey");
            if (preference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(stringValue));



                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            }

            return true;
        }
    }
}
