package com.djac21.washingtonpostnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private final static  String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "212c1dceeac8453d99337f0062e998f3";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(MainActivity.this, "No API key", Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new generateData().execute();
    }

    private class generateData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, null, "Loading, Please Wait...");
            super.onPreExecute();
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
            progressDialog.dismiss();
        }
    }
}