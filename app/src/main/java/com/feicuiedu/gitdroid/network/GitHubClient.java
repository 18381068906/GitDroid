package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.login.AccessTokenResult;
import com.feicuiedu.gitdroid.login.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by DELL on 2016/7/28.
 */
public class GitHubClient implements GitHubApi{
    private static GitHubClient gitHubClient;
    private GitHubApi gitHubApi;
    public static GitHubClient getInstance(){
        if (gitHubClient==null){
            gitHubClient = new GitHubClient();
        }
        return gitHubClient;
    }
    private GitHubClient() {
        OkHttpClient okHttpClient =new OkHttpClient.Builder().addInterceptor(new TokenInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        gitHubApi = retrofit.create(GitHubApi.class);
    }


    @Override
    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getOAuthToken(client,clientSecret,code);
    }
    @Override public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }
}
