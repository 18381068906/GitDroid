package com.feicuiedu.gitdroid.gank.network;

import android.content.Context;

import com.feicuiedu.gitdroid.commons.LoggingInterceptor;
import com.feicuiedu.gitdroid.gank.model.GankResult;
import com.feicuiedu.gitdroid.network.GitHubApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * Created by DELL on 2016/8/5.
 */
public class GankClient implements GankApi{
    private static GankClient gankClient;
    private GankApi gankApi;
    private GankClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://gank.io/api/")
                .client(okHttpClient)
                .build();
        gankApi = retrofit.create(GankApi.class);
    }
    public static GankClient getInstance(){
        if (gankClient==null){
            gankClient = new GankClient();
        }
        return gankClient;
    }

    @Override
    public Call<GankResult> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day) {
        return gankApi.getDailyData(year,month,day);
    }
}
