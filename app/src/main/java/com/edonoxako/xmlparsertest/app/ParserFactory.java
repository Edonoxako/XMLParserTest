package com.edonoxako.xmlparsertest.app;

import org.json.JSONObject;

/**
 * Created by EugeneM on 18.02.2015.
 */
public class ParserFactory {

    static private String JSON_URL = "http://www.floatrates.com/daily/usd.json";
    static private String XML_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private ParserFactory() {

    }

    public static Parser createParser(ParseCallback callback, boolean isXml) {
        if (isXml) {
            return new XMLParser(callback, XML_URL);
        } else {
            return new JSONParser(callback, JSON_URL);
        }
    }
}
