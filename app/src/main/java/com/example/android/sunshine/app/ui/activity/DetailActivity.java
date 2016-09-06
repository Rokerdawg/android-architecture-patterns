package com.example.android.sunshine.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.android.sunshine.app.R;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        ShareActionProvider mShareActionProvider;
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASHTAG = "#shareweather";
        private String mShareWeatherStr;
        TextView mWeatherView;
        Intent mWeatherInfoIntent;

        public DetailFragment() {
            // Set flag that this fragment has an OptionsMenu - else OptionsMenu wont be called
            setHasOptionsMenu(true);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            mWeatherView = (TextView)rootView.findViewById(R.id.weather_info_text);
            mWeatherInfoIntent = getActivity().getIntent();
            if (mWeatherInfoIntent != null && mWeatherInfoIntent.hasExtra(Intent.EXTRA_TEXT)){
                mShareWeatherStr = mWeatherInfoIntent.getStringExtra(Intent.EXTRA_TEXT);
                mWeatherView.setText(mShareWeatherStr);
            }

            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.detail_fragment, menu);
            menu.clear();
            MenuItem item = menu.findItem(R.id.share_weather_forecast);
            mShareActionProvider = (ShareActionProvider) item.getActionProvider();
            //Attach an intent to this ShareActionProvider
            if (mShareActionProvider != null){
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }else{
                Log.d(LOG_TAG, "onCreateOptionsMenu: Share Action Provider is null?");
            }

        }

        // Call to update the share intent
        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            // This flag ensures that on pressing back - you stay within calling application of
            // intent, not the application handling the share intent
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            // Let framework know that we are sharing plain text
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mShareWeatherStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
    }
}