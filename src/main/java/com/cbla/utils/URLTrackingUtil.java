package com.cbla.utils;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLTrackingUtil {

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();

        String[] pairs = query.split("&");
        for (String pair : pairs) {

            int idx = pair.indexOf("=");
            // Some Query Arguments might not have a value
            if (idx >= 0) {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        return query_pairs;
    }

    public static String getPathFromURL(String testURL) throws MalformedURLException {
        URL url = new URL(testURL);
        return url.getPath();
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

}
