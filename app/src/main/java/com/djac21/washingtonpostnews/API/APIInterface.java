package com.djac21.washingtonpostnews.API;

import com.djac21.washingtonpostnews.Models.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("everything?sources=the-washington-post")
    Call<NewsResponse> getTopNews(@Query("apiKey") String apiKey);
}