package com.kalpesh.googlenews.factories;

import android.text.TextUtils;

import com.kalpesh.googlenews.model.NewsReqRespConstants;
import com.kalpesh.googlenews.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by abc on 30-09-2014.
 */
public class NewsUrlFactory {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/news?v=1.0";
    private int searchResultPerPage = 8; //Default keeping it 8

    public void setSearchResultPerPage(int searchResultPerPage) {
        if (searchResultPerPage < 0 || searchResultPerPage > 8)
            throw new IllegalArgumentException("Search result per page should be between 1 to 8 inclusive");
        this.searchResultPerPage = searchResultPerPage;
    }

    public String getSearchUrl(String query) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);

        if (TextUtils.isEmpty(query))
            throw new IllegalArgumentException("Query String should not be empty or null.");

        try {
            urlBuilder.append("&" + NewsReqRespConstants.REQUEST_KEY_QUERY + "=" + URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Decide what to in case of encoding not supported.
        }

        urlBuilder.append("&" + NewsReqRespConstants.REQUEST_KEY_RESULTS_PER_PAGE + "=" + searchResultPerPage);

        String ipAddress = Utils.getLocalIpAddress();
        if (!TextUtils.isEmpty(ipAddress))
            urlBuilder.append("&" + NewsReqRespConstants.REQUEST_KEY_USER_IP + "=" + ipAddress);

        return urlBuilder.toString();
    }
}
