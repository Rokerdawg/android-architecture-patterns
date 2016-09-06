package com.example.android.sunshine.app.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.ui.fragment.ForecastFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    private void openPreferredLocationInMap(){

        // Reference the location stored in default shared prefs
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get string
        String location = sharedPreferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(buildGeoLocation(location));

        // Avoid crash by checking to see if recipient application can handle implicit request
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else {
            Log.d(TAG, "openPreferredLocationInMap: Couldnt call " + location
                    + ", no receiving apps installed");
        }

    }

    private Uri buildGeoLocation(String location) {
        return Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_map:
                openPreferredLocationInMap();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}

