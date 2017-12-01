package com.djac21.washingtonpostnews;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.djac21.washingtonpostnews.Adapter.NewsAdapter;
import com.djac21.washingtonpostnews.Model.NewsModel;
import com.djac21.washingtonpostnews.Model.NewsResponse;
import com.djac21.washingtonpostnews.API.ApiClient;
import com.djac21.washingtonpostnews.API.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "212c1dceeac8453d99337f0062e998f3";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);

        if (API_KEY.isEmpty()) {
            Toast.makeText(MainActivity.this, "No API key", Toast.LENGTH_LONG).show();
            return;
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetData().execute();
            }
        });

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<NewsResponse> call = apiService.getTopNews(API_KEY);
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    List<NewsModel> news = response.body().getResults();
                    recyclerView.setAdapter(new NewsAdapter(news, R.layout.list_items, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage("This app uses the Washington Post API to populate the data")
                    .setPositiveButton("OK", null);
            builder.create().show();
        } else if (id == R.id.action_refresh) {
            new GetData().execute();
        }
        return super.onOptionsItemSelected(item);
    }
}