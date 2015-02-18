package com.edonoxako.xmlparsertest.app;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EugeneM on 17.02.2015.
 */
public class JSONParser extends Parser {

    String LOG_TAG = "magic";

    public JSONParser(ParseCallback callback, String sourceUrl) {
        super(callback, sourceUrl);
    }

    @Override
    protected List<List<String>> parse(String rawData) {
        Log.d(LOG_TAG, "RAW: " + rawData);
        List<List<String>> rates = new ArrayList<List<String>>();
        List<String> rate;

        try {
            JSONObject rawRates = new JSONObject(rawData);
            JSONArray names = rawRates.names();

            for(int i = 0; i < names.length(); i++){
                String name = names.getString(i);
                JSONObject rawRate = rawRates.getJSONObject(name);

                rate = new ArrayList<String>();
                rate.add(rawRate.getString("code"));
                rate.add("1");
                rate.add(rawRate.getString("rate"));

                rates.add(rate);
            }

            rate = new ArrayList<String>();
            rate.add("USD");
            rate.add("1");
            rate.add("1");
            rates.add(rate);

            Log.d(LOG_TAG, "Names " + names.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return rates;
        }
    }
}
