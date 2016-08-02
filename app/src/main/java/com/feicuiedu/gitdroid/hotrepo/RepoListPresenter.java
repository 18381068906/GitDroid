package com.feicuiedu.gitdroid.hotrepo;

import android.widget.Toast;

import com.feicuiedu.gitdroid.network.GitHubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RepoListPresenter {
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;
    private Call<RepoResult> repoCall;

    public RepoListPresenter(RepoListView repoListView,Language language){
        this.repoListView = repoListView;
        this.language = language;
    }

    public void refresh() {
        repoListView.showContentView();
        nextPage = 1; // 永远刷新最新数据
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(repoCallback);
    }
    private Callback<RepoResult> repoCallback = new Callback<RepoResult>() {
        //成功

        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.stopRefresh();
            RepoResult repoResult = response.body();
            List<Repo> list = repoResult.getRepoList();
            repoListView.refreshData(list);

            }

        //失败
        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoListView.stopRefresh();
            repoListView.showContentView();
            repoListView.showToast("连接失败");
        }
    };

    // 加载更多处理
    public void loadMore() {
        repoListView.showLoadMoreLoading();
        repoCall = GitHubClient.getInstance().searchRepos(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(loadMoreCallback);
    }
    private Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMore();
            RepoResult repoResult = response.body();
            List<Repo> list = repoResult.getRepoList();
            repoListView.addMoreData(list);
            nextPage++;

        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            repoListView.hideLoadMore();
            repoListView.showToast("连接失败");
        }
    };
}
