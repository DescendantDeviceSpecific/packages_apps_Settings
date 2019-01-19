/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.content.ContentResolver;
import android.os.Bundle;
import android.text.TextUtils;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.settingslib.core.AbstractPreferenceController;

import libcore.util.Objects;
import java.util.ArrayList;
import java.util.List;


public class SystemIconSwitcherPreferenceController extends AbstractPreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String ICON_SELECTOR = "icon_selector";
    private ListPreference mICONstyle;

    public SystemIconSwitcherPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return ICON_SELECTOR;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mICONstyle = (ListPreference) screen.findPreference(ICON_SELECTOR);
        int ICONstyle = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.ICON_SELECTOR, 0);
        int valueIndex = mICONstyle.findIndexOfValue(String.valueOf(ICONstyle));
        mICONstyle.setValueIndex(valueIndex >= 0 ? valueIndex : 0);
        mICONstyle.setSummary(mICONstyle.getEntry());
        mICONstyle.setOnPreferenceChangeListener(this);
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mICONstyle) {
            String value = (String) newValue;
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.ICON_SELECTOR, Integer.valueOf(value));
            int valueIndex = mICONstyle.findIndexOfValue(value);
            mICONstyle.setSummary(mICONstyle.getEntries()[valueIndex]);
        }
        return true;
    }
}


