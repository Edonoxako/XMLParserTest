package com.edonoxako.xmlparsertest.app;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by EugeneM on 17.02.2015.
 */
public abstract class Parser extends AsyncTask<String, Void, Void> {

    private String encoding = "windows-1251";
    private String url;
    private ParseCallback callback;
    private List<List<String>> rates;

    protected String rawData;

    public Parser(ParseCallback callback, String sourceUrl) {
        this.callback = callback;
        this.url = sourceUrl;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            downloadData(params[0]);
            rates = parse(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.onParseDone(rates);
    }

    public void extractData() {
        this.execute(url);
    }

    private void downloadData(String sourceUrl) throws IOException {
        URL obj = new URL(sourceUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), encoding));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        rawData = response.toString();
    }

    protected abstract List<List<String>> parse(String rawData);
}
