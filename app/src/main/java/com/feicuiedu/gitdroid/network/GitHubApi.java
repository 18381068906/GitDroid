package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.hotrepo.RepoResult;
import com.feicuiedu.gitdroid.hotuser.UserResult;
import com.feicuiedu.gitdroid.login.AccessTokenResult;
import com.feicuiedu.gitdroid.login.User;
import com.feicuiedu.gitdroid.repoinfo.RepoContentResult;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    /**
     * 获取仓库
     * @Param query 查询参数(language:java)
     * @Param pageId 查询页数据(从1开始)
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepos(
            @Query("q")String query,
            @Query("page")int pageId);


    /***
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容,将是markdown格式且做了base64处理
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(
            @Path("owner") String owner,
            @Path("repo") String repo);

    /***
     * 获取一个markdonw内容对应的HTML页面
     * @param body 请求体,内容来自getReadme后的RepoContentResult
     */
    @Headers({
            "Content-Type:text/plain"
    })
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

    /**
     *
     */
    @GET("/search/users?q=followers:%3E1000")
    Call<UserResult> getUserList(@Query("page")int page);
}
