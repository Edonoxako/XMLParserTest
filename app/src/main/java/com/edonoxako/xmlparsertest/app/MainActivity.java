package com.edonoxako.xmlparsertest.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ParseCallback{

    private TextView dataTV;
    private Button refreshJSONBtn;
    private Button refreshXMLBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTV = (TextView) findViewById(R.id.dataTextView);
        refreshJSONBtn = (Button) findViewById(R.id.refreshJSONButton);
        refreshXMLBtn = (Button) findViewById(R.id.refreshXMLButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        refreshJSONBtn.setOnClickListener(this);
        refreshXMLBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dataTV.setText("");
        boolean isXml = false;
        switch (v.getId()) {
            case R.id.refreshJSONButton:
                isXml = false;
                break;

            case R.id.refreshXMLButton:
                isXml = true;
                break;
        }
        progressBar.setVisibility(View.VISIBLE);
        Parser parser = ParserFactory.createParser(this, isXml);
        parser.extractData();
    }

    @Override
    public void onParseDone(List<List<String>> rates) {
        progressBar.setVisibility(View.GONE);
        if (rates.isEmpty()) {
            dataTV.setText("Error. Data unavailable");
            return;
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
