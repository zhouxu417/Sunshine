package com.zhouxu417.xu.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;

import com.zhouxu417.xu.sunshine.data.WeatherContract;
import com.zhouxu417.xu.sunshine.data.WeatherContract.WeatherEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = "#Sunshine";
    private String mForecastStr ;
    private ShareActionProvider mShareActionProvider;
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            WeatherEntry.TABLE_NAME + "." + WeatherEntry._ID,
            WeatherEntry.COLUMN_DATE,
            WeatherEntry.COLUMN_SHORT_DESC,
            WeatherEntry.COLUMN_MAX_TEMP,
            WeatherEntry.COLUMN_MIN_TEMP,
            WeatherEntry.COLUMN_HUMIDITY,
            WeatherEntry.COLUMN_PRESSURE,
            WeatherEntry.COLUMN_WIND_SPEED,
            WeatherEntry.COLUMN_DEGREES,
            WeatherEntry.COLUMN_WEATHER_ID,
            // This works because the WeatherProvider returns location data joined with
            // weather data, even though they're stored in two different tables.
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
    };

    public static final int COL_WEATHER_ID = 0;
    public static final int COL_WEATHER_DATE = 1;
    public static final int COL_WEATHER_DESC = 2;
    public static final int COL_WEATHER_MAX_TEMP = 3;
    public static final int COL_WEATHER_MIN_TEMP = 4;
    public static final int COL_WEATHER_HUMIDITY = 5;
    public static final int COL_WEATHER_PRESSURE = 6;
    public static final int COL_WEATHER_WIND_SPEED = 7;
    public static final int COL_WEATHER_DEGREES = 8;
    public static final int COL_WEATHER_CONDITION_ID = 9;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) rootview.findViewById(R.id.detail_textView))
                    .setText(mForecastStr);
        }
        return rootview;
    }

    private Intent createShareForecastIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detail, menu);
        //menu.add(0, 0, 0, "退出");
        //return null;
        MenuItem menuItem  = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if(mForecastStr != null){
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }else {
            Log.d(LOG_TAG, "Share Action Provider is NULL ?");
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            // Read weather condition ID from cursor
//            int weatherId = data.getInt(COL_WEATHER_CONDITION_ID);
//
//            // Use weather art image
//            mIconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
//
//            // Read date from cursor and update views for day of week and date
//            long date = data.getLong(COL_WEATHER_DATE);
//            String friendlyDateText = Utility.getDayName(getActivity(), date);
//            String dateText = Utility.getFormattedMonthDay(getActivity(), date);
//            mFriendlyDateView.setText(friendlyDateText);
//            mDateView.setText(dateText);
//
//            // Read description from cursor and update view
//            String description = data.getString(COL_WEATHER_DESC);
//            mDescriptionView.setText(description);
//
//            // For accessibility, add a content description to the icon field
//            mIconView.setContentDescription(description);
//
//            // Read high temperature from cursor and update view
//            boolean isMetric = Utility.isMetric(getActivity());
//
//            double high = data.getDouble(COL_WEATHER_MAX_TEMP);
//            String highString = Utility.formatTemperature(getActivity(), high);
//            mHighTempView.setText(highString);
//
//            // Read low temperature from cursor and update view
//            double low = data.getDouble(COL_WEATHER_MIN_TEMP);
//            String lowString = Utility.formatTemperature(getActivity(), low);
//            mLowTempView.setText(lowString);
//
//            // Read humidity from cursor and update view
//            float humidity = data.getFloat(COL_WEATHER_HUMIDITY);
//            mHumidityView.setText(getActivity().getString(R.string.format_humidity, humidity));
//
//            // Read wind speed and direction from cursor and update view
//            float windSpeedStr = data.getFloat(COL_WEATHER_WIND_SPEED);
//            float windDirStr = data.getFloat(COL_WEATHER_DEGREES);
//            mWindView.setText(Utility.getFormattedWind(getActivity(), windSpeedStr, windDirStr));
//
//            // Read pressure from cursor and update view
//            float pressure = data.getFloat(COL_WEATHER_PRESSURE);
//            mPressureView.setText(getActivity().getString(R.string.format_pressure, pressure));
//
//            // We still need this for the share intent
//            mForecast = String.format("%s - %s - %s/%s", dateText, description, high, low);
            String dateString = Utility.formatDate(data.getLong(COL_WEATHER_DATE));
            String weatherDescription = data.getString(COL_WEATHER_DESC);
            boolean ismetric = Utility.isMetric(getActivity());
            String high = Utility.formatTemperature(data.getDouble(COL_WEATHER_MAX_TEMP), ismetric);
            String low = Utility.formatTemperature(data.getDouble(COL_WEATHER_MIN_TEMP), ismetric);
            mForecastStr = String.format("%s - %s - %s/%s" , dateString, weatherDescription, high, low);

            TextView datailTextView = (TextView)getView().findViewById(R.id.detail_textView);
            datailTextView.setText(mForecastStr);
            // If onCreateOptionsMenu has already happened, we need to update the share intent now.
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}
