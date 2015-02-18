package com.edonoxako.xmlparsertest.app;

import java.util.List;

/**
 * Created by EugeneM on 17.02.2015.
 */
public interface ParseCallback {
    public void onParseDone(List<List<String>> rates);
}
