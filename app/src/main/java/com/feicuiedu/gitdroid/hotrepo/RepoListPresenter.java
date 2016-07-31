package com.feicuiedu.gitdroid.hotrepo;

import android.os.AsyncTask;
import android.widget.Toast;

import com.feicuiedu.gitdroid.network.GitHubApi;
import com.feicuiedu.gitdroid.network.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RepoListPresenter {
    private RepoListView repoListView;
    private int count;


    public RepoListPresenter(RepoListView repoListView){
        this.repoListView = repoListView;
    }

    public void refresh() {
//        new RefreshTask().execute();
//        GitHubClient gitHubClient= new GitHubClient();
//        GitHubApi gitHubApi = gitHubClient.getGitHubApi();
//        Call<ResponseBody> call = gitHubApi.getRetrofitContributors();
//        //异步获取数据
//        call.enqueue(callback);
    }
    private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        //成功

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoListView.stopRefresh();
            if (response.isSuccessful()){
                try {
                    ResponseBody body = response.body();
                    if (body == null){
                        return;
                    }
                    String text = body.string();
                    repoListView.showContentView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //失败
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoListView.stopRefresh();
            repoListView.showContentView();
        }
    };
    class RefreshTask extends AsyncTask<Void,Void,Void> {
        @Override protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for(int i=0; i<20; i++){
                datas.add("测试数据 " + (count++));
            }
            repoListView.stopRefresh();
            repoListView.refreshData(datas);
            repoListView.showContentView();
        }
    }
    // 加载更多处理
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        new LoadMoreTask().execute();
    }

    final class LoadMoreTask extends AsyncTask<Void, Void, Void> {
        @Override protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for (int i = 0; i < 20; i++) {
                datas.add("测试数据 " + (count++));
            }
            repoListView.addMoreData(datas);
            repoListView.hideLoadMore();
        }
    }
}
