package com.feicuiedu.gitdroid.login;

/**
 * Created by Administrator on 2016/7/31.
 */
public interface LoginView {

    // 显示进度
    void showProgress();

    void showMessage(String msg);

    // 重置WebView
    void resetWeb();

    // 进入主界面
    void navigateToMain();
}
