package com.kalpesh.googlenews.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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
import com.kalpesh.googlenews.utils.LogUtils;

import org.json.JSONException;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {
    private static final String TAG = MyActivity.class.getSimpleName();
    private ListView newsListView;
    private NewsListAdapter newsAdapter;
    private ArrayList<NewsItem> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        newsListView = (ListView) findViewById(R.id.news_list_view);

        String url = new NewsUrlFactory().getSearchUrl("Barak Obama");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!TextUtils.isEmpty(response)){
                    try {
                        NewsResponse newsResponse = new NewsResponse(response);
                        newsItems = newsResponse.getNewsItems();
                        if(newsItems != null && newsItems.size() >0){
                            newsAdapter = new NewsListAdapter(newsItems, getLayoutInflater());
                            newsListView.setAdapter(newsAdapter);
                        }
                    } catch (JSONException e) {
                        //TODO JSON Exception Invalid Response
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
