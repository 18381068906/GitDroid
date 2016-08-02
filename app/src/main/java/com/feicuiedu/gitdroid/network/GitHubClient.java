package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.hotrepo.RepoResult;
import com.feicuiedu.gitdroid.hotuser.UserResult;
import com.feicuiedu.gitdroid.login.AccessTokenResult;
import com.feicuiedu.gitdroid.login.User;
import com.feicuiedu.gitdroid.repoinfo.RepoContentResult;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
        return gitHubApi.getOAuthToken(client, clientSecret, code);
    }
    @Override public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }

    @Override
    public Call<RepoResult> searchRepos(@Query("q") String query, @Query("page") int pageId) {
        return gitHubApi.searchRepos(query, pageId);
    }
    @Override public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return gitHubApi.getReadme(owner, repo);
    }

    @Override public Call<ResponseBody> markDown(@Body RequestBody body) {
        return gitHubApi.markDown(body);
    }

    @Override
    public Call<UserResult> getUserList(@Query("page") int page) {
        return gitHubApi.getUserList(page);
    }


}
