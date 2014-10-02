package com.kalpesh.googlenews.model;

import com.kalpesh.googlenews.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abc on 30-09-2014.
 */
public class NewsResponse {

    private ArrayList<NewsItem> newsItems;
    private boolean responseSuccess;
    private String responseDetails;

    public ArrayList<NewsItem> getNewsItems() {
        return newsItems;
    }

    public boolean isResponseSuccess() {
        return responseSuccess;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public NewsResponse(String jsonResponse) throws JSONException {
        JSONObject respObject = new JSONObject(jsonResponse);
        responseDetails = respObject.optString(NewsReqRespConstants.RESPONSE_KEY_RESP_DETAILS);
        responseSuccess = respObject.optInt(NewsReqRespConstants.RESPONSE_KEY_RESP_STATUS) == NewsReqRespConstants.RESPONSE_SUCCESS;
        if (responseSuccess) {
            JSONObject responseDate = respObject.optJSONObject(NewsReqRespConstants.RESPONSE_KEY_RESP_DATA);
            if (responseDate != null) {

                //Parsing for News data.
                JSONArray resultArray = responseDate.optJSONArray(NewsReqRespConstants.RESPONSE_KEY_RESULTS);
                if (resultArray != null && resultArray.length() > 0) {
                    newsItems = new ArrayList<NewsItem>();
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject individualResult = resultArray.optJSONObject(i);
                        if (individualResult != null) {
                            JSONObject imageObject = individualResult.optJSONObject(NewsReqRespConstants.RESPONSE_KEY_IMAGE);
                            String imageUrl = null;
                            if (imageObject != null)
                                imageUrl = imageObject.optString(NewsReqRespConstants.RESPONSE_KEY_IMAGE_URL);
                            String title = individualResult.optString(NewsReqRespConstants.RESPONSE_KEY_TITLE);
                            String publisherUrl = individualResult.optString(NewsReqRespConstants.RESPONSE_KEY_PUBLISHER_URL);
                            Date publishDate = Utils.convertStringToDate(individualResult.optString(NewsReqRespConstants.RESPONSE_KEY_PUBLISH_DATE));
                            NewsItem newsItem = new NewsItem(title, publishDate, publisherUrl, imageUrl);
                            newsItems.add(newsItem);
                        }
                    }
                }
            }
        }
    }
}
