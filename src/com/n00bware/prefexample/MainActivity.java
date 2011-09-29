/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *                    Version 2, December 2004 
 *
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 
 *
 * Everyone is permitted to copy and distribute verbatim or modified 
 * copies of this license document, and changing it is allowed as long 
 * as the name is changed. 
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO. 
 */

package com.n00bware.prefexample;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    private String PREF_EDIT_HOLDER; //Holds new value of EditTextPreference
    private static final String PREF_EDIT_TEXT = "pref_edit_text"; //handle to find this EditTextPreference (android:key)
    private static final String TAG = "PrefExample"; //TAG to identify app to logcat
    private static final String PREF_LIST = "pref_list"; //handle to find the ListPreference (android:key)

    private EditTextPreference mEditTextPref; //Object used to reference EditTextPreference
    private ListPreference mListPref; //Object used to reference ListPreference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // called when first created

        //set the title of the view
        setTitle(R.string.main_title_head);

        //load the preferences from res/xml/main.xml
        addPreferencesFromResource(R.xml.main); 

        //send logcat a message for debuging
        Log.d(TAG, "Loading prefs");

        //we neet get the screen we are using so we can reference our preferences (Objects)
        PreferenceScreen prefSet = getPreferenceScreen();

        //assign mEditTextPref to work with android:key="pref_edit_text"
        mEditTextPref = (EditTextPreference) prefSet.findPreference(PREF_EDIT_TEXT);

        //if mEditTextPref isn't empty (null) then get the text and send it to a string (PREF_EDIT_HOLDER)
        if (mEditTextPref != null) {
            EditText editText = mEditTextPref.getEditText();
            PREF_EDIT_HOLDER = mEditTextPref.getEditText().toString();

            //check if the editText widget is empty if not then
            if (editText != null){

                //we want to limit the length of the input to 30char
                InputFilter lengthFilter = new InputFilter.LengthFilter(30);
                editText.setFilters(new InputFilter[]{lengthFilter});
                //we only want one line of text from the user
                editText.setSingleLine(true);
            }
        }
        /*
         * This is the most important part setup a listener for change
         * this lets us use the onPreferenceChange method below
         */
        mEditTextPref.setOnPreferenceChangeListener(this);

        //this is simplier
        mListPref = (ListPreference) prefSet.findPreference(PREF_LIST);
        mListPref.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference pref, Object newValue) {

        //we want to handle "unknown" differently than anything else
        //so we take care of it first
        if (newValue.toString().equals("unknown")) {
            //do work
            Log.d(TAG, "method recieved jinx **DO SOMETHING SPECIAL**");
            Toast.makeText(MainActivity.this, "method recieved \"jinx\" **DO SOMETHING SPECIAL**", Toast.LENGTH_LONG).show();
            return true; //we used the valuse so return true
        //now we handle everything that doesn't have a newValue of "jinx"
        } else {
            if (pref == mEditTextPref) {
                //do work
                Toast.makeText(MainActivity.this, "You chose " + pref + " with a new value of " + newValue.toString(), Toast.LENGTH_LONG).show();
                return true; //we used the values so return true

            //handle all the ListPreference values except "jinx"
            } else if (pref == mListPref) {
                //do work
                Toast.makeText(MainActivity.this, "You chose " + pref + " with a new value of " + newValue.toString(), Toast.LENGTH_LONG).show();
                return true; //we used the values so return true
            }
        return false; //pref selected but not newValue so return false
        }
    }
}
