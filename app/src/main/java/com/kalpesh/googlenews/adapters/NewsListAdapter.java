package com.kalpesh.googlenews.adapters;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kalpesh.googlenews.R;
import com.kalpesh.googlenews.app.AppController;
import com.kalpesh.googlenews.model.NewsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abc on 01-10-2014.
 */
public class NewsListAdapter extends BaseAdapter {

    private ArrayList<NewsItem> newsItems;
    private LayoutInflater layoutInflater;

    public NewsListAdapter(ArrayList<NewsItem> newsItems, LayoutInflater layoutInflater) {
        this.newsItems = newsItems;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.news_item_card, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.news_date);
            viewHolder.ivNewsImage = (NetworkImageView) convertView.findViewById(R.id.news_image_view);
            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        NewsItem newsItem = newsItems.get(position);
        String title = newsItem.getTitle();
        if(!TextUtils.isEmpty(title))
            viewHolder.tvTitle.setText(Html.fromHtml(title));

        String imageUrl = newsItem.getImageUrl();
        if(!TextUtils.isEmpty(imageUrl)){
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.ivNewsImage.setImageUrl(imageUrl, imageLoader);
        }else{
            //TODO put Error Image in to Image View.
        }

        Date date = newsItem.getPublishDate();
        if(date != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            viewHolder.tvDate.setText(dateFormat.format(date));
        }
        return convertView;
    }

    public class ViewHolder{
        TextView tvTitle;
        TextView tvDate;
        NetworkImageView ivNewsImage;
    }
}
