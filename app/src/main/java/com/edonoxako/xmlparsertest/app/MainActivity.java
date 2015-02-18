package com.edonoxako.xmlparsertest.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ParseCallback{

    private String URL = "http://www.floatrates.com/daily/usd.json";

    private TextView dataTV;
    private Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTV = (TextView) findViewById(R.id.dataTextView);
        refreshBtn = (Button) findViewById(R.id.refreshButton);

        refreshBtn.setOnClickListener(this);
    }

    private void getData() {
        Parser parser = new JSONParser(this, URL);
        parser.extractData();
    }

    @Override
    public void onClick(View v) {
        getData();
    }

    @Override
    public void onParseDone(List<List<String>> rates) {
        if (rates.isEmpty()) {
            dataTV.setText("Error. Data unavailable");
        }

        String res = "";
        for (List<String> item : rates) {
            res += "Currency: " + item.get(0) + "\n" +
                    "Nominal: " + item.get(1) + "\n" +
                    "Value: " + item.get(2) + "\n";

            dataTV.setText(res);
        }
    }
}
