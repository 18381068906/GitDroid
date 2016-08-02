package com.feicuiedu.gitdroid.hotuser;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.feicuiedu.gitdroid.network.GitHubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 2016/8/2.
 */
public class HotUserPresenter {
    private Call<UserResult> userResultCall;
    private HotUserView hotUserView;
    private int page = 1;
    public HotUserPresenter(@NonNull HotUserView hotUserView){
        this.hotUserView = hotUserView;
    }
    public void refresh(){
        if (userResultCall!=null){
            userResultCall.cancel();
        }
        page = 1;
        userResultCall = GitHubClient.getInstance().getUserList(page);
        userResultCall.enqueue(userCallBack);
    }
    private Callback<UserResult> userCallBack = new Callback<UserResult>() {
        @Override
        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
            hotUserView.stopRefresh();
            UserResult userResult = response.body();
            hotUserView.refresh(userResult.getUserList());
            page = 2;
        }

        @Override
        public void onFailure(Call<UserResult> call, Throwable t) {
            hotUserView.stopRefresh();
            hotUserView.showToast("连接失败");
        }
    };
    public void loadMore(){
        hotUserView.showLoadMore();
        if (userResultCall!=null){
            userResultCall.cancel();
        }
        userResultCall = GitHubClient.getInstance().getUserList(page);
        userResultCall.enqueue(loadMoreCallBack);
    }
    private Callback<UserResult> loadMoreCallBack = new Callback<UserResult>() {
        @Override
        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
            hotUserView.stopLoad();
            UserResult userResult = response.body();
            hotUserView.loadMore(userResult.getUserList());
            page++;
        }

        @Override
        public void onFailure(Call<UserResult> call, Throwable t) {
            hotUserView.stopLoad();
            hotUserView.showToast("连接失败");
        }
    };
    interface HotUserView{
        void refresh(List<UserList> userList);
        void loadMore(List<UserList> userList);
        void stopRefresh();
        void stopLoad();
        void showLoadMore();
        void showToast(String msg);
    }
}
