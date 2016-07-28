package com.feicuiedu.gitdroid.hotrepo;

import java.util.List;

/**
 * Created by DELL on 2016/7/28.
 */
public interface RepoListLoadMoreView {
    void showLoadMoreLoading();

    void hideLoadMore();

    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<String> datas);
}
