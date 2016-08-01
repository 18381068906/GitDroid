package com.feicuiedu.gitdroid.hotrepo;


import java.util.List;

/**
 * Created by DELL on 2016/7/28.
 */
public interface RepoListPtrView {

    public void showContentView() ;

    public void showErrorView(String errorMsg);

    public void showEmptyView();

    public void stopRefresh();
    public void refreshData(List<Repo> data);

}
