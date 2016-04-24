package com.zhouxu417.xu.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Sunshine_MainActivityFragment extends Fragment {

    static ArrayAdapter<String> mForecastAdapter;
    public Sunshine_MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootview = inflater.inflate(R.layout.fragment_sunshine__main, container, false);

        SharedPreferences shareprefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String unitsType = shareprefs.getString("units", "metric");

        String[] forecastArray = {
/*                "Today - Sunny - 25/13",
                "Tomorrow - Foggy - 23/10",
                "Weds - Cloudy - 22/11",
                "Thurs - Rain - 20/9",
                "Sat - Sunny - 27/14",
                "Sun - Sunny - 21/12"*/
        };
        List<String> weekForecast = new ArrayList<String>(
                Arrays.asList(forecastArray)
        );
         mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_sunshine,
                R.id.list_item_sunshine_textview,
                weekForecast);

        ListView listView =  (ListView) rootview.findViewById(R.id.sunshine_listView);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = mForecastAdapter.getItem(position);
                //Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_sunshine__main, menu);
        //menu.add(0, 0, 0, "退出");
        //return null;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        if(id == R.id.action_map){

        }
        return super.onOptionsItemSelected(item);
    }

    public String getPreference(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String units = prefs.getString(getString(R.string.pref_key_units), getString(R.string.pref_units_metric));
        return units ;
    }



}
