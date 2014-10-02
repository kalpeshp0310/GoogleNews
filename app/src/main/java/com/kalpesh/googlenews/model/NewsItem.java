package com.kalpesh.googlenews.model;

import java.util.Date;

/**
 * Created by abc on 30-09-2014.
 */
public class NewsItem {
    private String title;
    private Date publishDate;
    private String publisherUrl;
    private String imageUrl;

    public NewsItem(String title, Date publishDate, String publisherUrl, String imageUrl) {
        this.title = title;
        this.publishDate = publishDate;
        this.publisherUrl = publisherUrl;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
