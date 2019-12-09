package com.studio.tiny.dbexcercise;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefActivity extends Activity {
    private static final String NICKNAME = "nickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_fragment);
    }

    public static class MujPreferenceFragment extends PreferenceFragment {
        protected static final String TAG = "MojPrefsFragment";
        private SharedPreferences.OnSharedPreferenceChangeListener listener;
        private Preference preference;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.nick_prefs);
            preference = (Preference) getPreferenceManager()
                    .findPreference(NICKNAME);
            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged
                        (SharedPreferences sp, String key) {
                    preference.setSummary(sp.getString(NICKNAME, "---"));
                }
            };
            SharedPreferences prefs =
                    getPreferenceManager().getSharedPreferences();
            prefs.registerOnSharedPreferenceChangeListener(listener);
            listener.onSharedPreferenceChanged(prefs, NICKNAME);
        }
    }
}


