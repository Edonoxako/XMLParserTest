package com.edonoxako.xmlparsertest.app;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EugeneM on 09.02.2015.
 */
public class XMLParser {

    String xmlEncoding = "windows-1251";
    String requestRes;

    Document doc;

    String LOG_TAG = "magic";

    public XMLParser() throws IOException, ParserConfigurationException, SAXException {
        String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), xmlEncoding));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        requestRes = response.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputStream in = con.getInputStream();
        doc = dBuilder.parse(in);
        doc.getDocumentElement().normalize();
        in.close();
    }

    public List<List<String>> parse() {
        Log.d(LOG_TAG, "Начали парсить");
        NodeList nList = doc.getElementsByTagName("Cube")/*.item(0).getChildNodes().item(0).getChildNodes()*/;
        Log.d(LOG_TAG, "Lenght: " + nList.getLength());
        List<List<String>> rates = new ArrayList<List<String>>();
        List<String> data;

        for(int i = 2; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            Element eElement = (Element) nNode;
            data = new ArrayList<String>(3);
            data.add(0, eElement.getAttribute("currency"));
            data.add(1, "1");
            data.add(2, eElement.getAttribute("rate").replace(",", "."));

            Log.d(LOG_TAG, data.get(0));
            Log.d(LOG_TAG, data.get(1));
            Log.d(LOG_TAG, data.get(2));
            Log.d(LOG_TAG, "---------------------");

            rates.add(data);
        }

        data = new ArrayList<String>(3);
        data.add(0, "EUR");
        data.add(1, "1");
        data.add(2, "1");
        rates.add(data);
        Log.d(LOG_TAG, "Закончили парсить");
        return rates;
    }
}
