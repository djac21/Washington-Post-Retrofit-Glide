package com.djac21.washingtonpostnews.API;

import com.djac21.washingtonpostnews.Model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("articles?source=the-washington-post&sortBy=top")
    Call<NewsResponse> getTopNews(@Query("apiKey") String apiKey);
}