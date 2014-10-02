package com.kalpesh.googlenews.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kalpesh.googlenews.R;
import com.kalpesh.googlenews.adapters.NewsListAdapter;
import com.kalpesh.googlenews.app.AppController;
import com.kalpesh.googlenews.factories.NewsUrlFactory;
import com.kalpesh.googlenews.model.NewsItem;
import com.kalpesh.googlenews.model.NewsResponse;
import com.kalpesh.googlenews.utils.AppMsgUtils;
import com.kalpesh.googlenews.utils.Utils;

import org.json.JSONException;

import java.util.ArrayList;


public class NewsListActivity extends ActionBarActivity {
    private static final String TAG = NewsListActivity.class.getSimpleName();
    private ListView newsListView;
    private NewsListAdapter newsAdapter;
    private ArrayList<NewsItem> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initListView();
        getAndShowNews("Barack Obama");
    }


    private void initListView() {
        newsListView = (ListView) findViewById(R.id.news_list_view);
        newsListView.setVerticalScrollBarEnabled(false);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsListAdapter newsListAdapter = (NewsListAdapter)parent.getAdapter();
                String publisherUrl = newsListAdapter.getNewsItems().get(position).getPublisherUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(publisherUrl));
                startActivity(intent);
            }
        });
    }

    private void getAndShowNews(String query) {
        if (Utils.isNetworkAvailable(NewsListActivity.this)) {
            String url = new NewsUrlFactory().getSearchUrl(query);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.news_loading_msg));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (!TextUtils.isEmpty(response)) {
                        try {
                            NewsResponse newsResponse = new NewsResponse(response);
                            newsItems = newsResponse.getNewsItems();
                            if (newsItems != null && newsItems.size() > 0) {
                                newsAdapter = new NewsListAdapter(newsItems, getLayoutInflater());
                                newsListView.setAdapter(newsAdapter);
                            } else {
                                AppMsgUtils.showAlertMsg(NewsListActivity.this, getString(R.string.no_news_found_msg));
                            }
                        } catch (JSONException e) {
                            AppMsgUtils.showAlertMsg(NewsListActivity.this, getString(R.string.invalid_response_msg));
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    AppMsgUtils.showAlertMsg(NewsListActivity.this, getString(R.string.invalid_response_msg));
                }
            });
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            AppMsgUtils.showAlertMsg(NewsListActivity.this, getString(R.string.no_internet_connection_msg));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Utils.isNetworkAvailable(this)) // If Network connection  available then only show filter action.
            getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter && newsAdapter != null) { // check for nullness of news adapter. If null that means no Items in listView
            AlertDialog.Builder filterDialog = new AlertDialog.Builder(this);
            filterDialog.setTitle(R.string.news_filter_title).setItems(R.array.news_filter_action_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0: //This means All Item selected
                            newsAdapter.updateNewsItems(newsItems);
                            break;
                        case 1: // This means past 1 Day Selected
                            newsAdapter.updateNewsItems(getFilteredNewsList(1));
                            break;
                        case 2: //This means past 7 Days Selected
                            newsAdapter.updateNewsItems(getFilteredNewsList(7));
                            break;
                        case 3:// This means past 30 Days Selected
                            newsAdapter.updateNewsItems(getFilteredNewsList(30));
                            break;
                    }
                }
            });
            filterDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Filteres newItems arrayList according to provided Days
    private ArrayList<NewsItem> getFilteredNewsList(int numberOfDays){
        ArrayList<NewsItem> filteredNewsList = new ArrayList<NewsItem>();
        for(NewsItem newsItem : newsItems){
            if(!Utils.isDateBeforeDays(newsItem.getPublishDate(), numberOfDays))
                filteredNewsList.add(newsItem);
        }
        return filteredNewsList;
    }
}
