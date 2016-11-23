package com.reddit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.reddit.R;
import com.reddit.adapter.FeedAdapter;
import com.reddit.appconstants.AppConstants;
import com.reddit.jsonmodels.RedditFeeds;
import com.reddit.networkservice.FeedsService;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FeedActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private ListView feedsListView;
    private Context mContext;
    private ProgressDialog progressDialog;
    private RedditFeeds redditFeeds;
    private FeedAdapter feedAdapter;
    Handler refreshHandler;
    Runnable refreshTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mContext = this;
        initFeedViews();
        initHttpClient();
        initProgressDialog();
        refreshHandler = new Handler();
        initListViewClickListner();
        fetchRedditFeeds(true);
    }

    public void initRefreshTask() {
        refreshTask = new Runnable() {
            @Override
            public void run() {
                fetchRedditFeeds(false);
                refreshHandler.postDelayed(refreshTask, AppConstants.REFRESH_TIME);
            }
        };
    }

    public void initFeedViews() {
        feedsListView = (ListView) findViewById(R.id.feeds_list_view);
    }

    public void initHttpClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(AppConstants.READ_TIME_OUT, TimeUnit.MILLISECONDS);
    }

    public void initProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    public void fetchRedditFeeds(boolean showProgress) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        FeedsService feedsService = retrofit.create(FeedsService.class);
        Call<RedditFeeds> call = feedsService.getRedditFeeds();
        if (showProgress) progressDialog.show();
        call.enqueue(new Callback<RedditFeeds>() {
            @Override
            public void onResponse(Response<RedditFeeds> response, Retrofit retrofit) {
                redditFeeds = response.body();
                if (feedAdapter == null) {
                    feedAdapter = new FeedAdapter(mContext, redditFeeds.getData().getChildren());
                    feedsListView.setAdapter(feedAdapter);
                } else {
                    feedAdapter.refreshAdapter(redditFeeds.getData().getChildren());
                }
                progressDialog.dismiss();
                refreshHandler.postDelayed(refreshTask, AppConstants.REFRESH_TIME);
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRefreshTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelRefreshTask();
    }

    public void cancelRefreshTask() {
        refreshHandler.removeCallbacks(refreshTask);
    }

    public void initListViewClickListner() {
        feedsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String feedDetailUrl = AppConstants.BASE_URL + redditFeeds.getData().
                        getChildren().get(i).getData().getPermalink();
                Intent intent = new Intent(mContext, FeedDetailActivity.class);
                intent.putExtra(AppConstants.FEED_DETAIL_URL, feedDetailUrl);
                startActivity(intent);
            }
        });
    }
}
