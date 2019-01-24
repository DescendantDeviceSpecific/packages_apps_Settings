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

import com.android.internal.util.descendant.Utils;
import libcore.util.Objects;
import java.util.ArrayList;
import java.util.List;


public class UIPreferenceController extends AbstractPreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String UI_SWITCHER = "ui_switcher";
    private ListPreference mUIstyle;

    public UIPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return UI_SWITCHER;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mUIstyle = (ListPreference) screen.findPreference(UI_SWITCHER);
        int UIstyle = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.UI_SWITCHER, 0);
        int valueIndex = mUIstyle.findIndexOfValue(String.valueOf(UIstyle));
        mUIstyle.setValueIndex(valueIndex >= 0 ? valueIndex : 0);
        mUIstyle.setSummary(mUIstyle.getEntry());
        mUIstyle.setOnPreferenceChangeListener(this);
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mUIstyle) {
            String value = (String) newValue;
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.UI_SWITCHER, Integer.valueOf(value));
            int valueIndex = mUIstyle.findIndexOfValue(value);
            mUIstyle.setSummary(mUIstyle.getEntries()[valueIndex]);
        }
        return true;
    }
}



