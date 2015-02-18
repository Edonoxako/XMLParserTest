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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EugeneM on 09.02.2015.
 */
public class XMLParser extends Parser {

    String LOG_TAG = "magic";

    public XMLParser(ParseCallback callback, String sourceUrl) {
        super(callback, sourceUrl);
    }

    @Override
    protected List<List<String>> parse(String rawData) {
        Log.d(LOG_TAG, "Начали парсить");

        List<List<String>> rates = new ArrayList<List<String>>();
        List<String> data;

        Document doc = convertToDocument(rawData);
        if (doc == null) {
            return rates;
        }
        NodeList nList = doc.getElementsByTagName("Cube")/*.item(0).getChildNodes().item(0).getChildNodes()*/;
        Log.d(LOG_TAG, "Lenght: " + nList.getLength());


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

    private Document convertToDocument(String data) {
        Document doc = null;
        try(InputStream in = new ByteArrayInputStream(data.getBytes())) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return doc;
        }
    }
}
