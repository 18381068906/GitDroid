package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.login.AccessTokenResult;
import com.feicuiedu.gitdroid.login.User;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by DELL on 2016/7/28.
 */
public interface GitHubApi {
    String CLIENT_ID = "2d58ee63c1f22faccbdc";
    String CLIENT_SECRET = "0a396c26b6314a94c526cb23b0504ad9bac17515";
    String CALL_BACK = "luodan";
    /**
     *user : 读写用户信息；
     *public_repo : 该用户公有库的访问权限，关注(starring)其他公有库的权限;
     *repo : 该用户公有和私有库的访问权限。
     */

    String AUTH_SCOPE = "user,public_repo,repo";
//    登录的url，
// CLIENT_ID 这是当你在GitHub上注册App时获得的Client Id
    String AUTH_RUL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    /**
     *  获取token api
     *  Headers :转化为需要的json类型的返回
     *
     */

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    @GET("user")
    Call<User> getUserInfo();
}
