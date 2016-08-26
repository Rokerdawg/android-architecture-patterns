package com.example.android.sunshine.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rmcg2 on 25/08/2016.
 */
public class WeatherDataParser {

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the max temp for the day indicated byt the dayIndex
     * (Note: 0- indexed, so 0 would refer to the first day
     */

    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException{
        JSONObject weatherObj = new JSONObject(weatherJsonStr);  //Turn the jsonstring into a jsonObject
        JSONArray days = weatherObj.getJSONArray("list");  //Then we look for the 'list' array
        JSONObject dayInfo = days.getJSONObject(dayIndex); //within that, we find the day we care about
        JSONObject temperatureInfo = dayInfo.getJSONObject("temp"); // then we look for the temp node
        return temperatureInfo.getDouble("max");  //then we find the max temp
    }

}
