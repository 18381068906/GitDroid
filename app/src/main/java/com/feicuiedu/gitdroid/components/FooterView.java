package com.feicuiedu.gitdroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 2016/7/28.
 */
public class FooterView extends FrameLayout {
    //定义加载状态
    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;

    private int state = STATE_LOADING;

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.tv_complete)TextView tvComplete;
    @BindView(R.id.tv_error)TextView tvError;
    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        ButterKnife.bind(this);

    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_load_footer,this,true);

    }

    public FooterView(Context context) {
        this(context,null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    /** 是否正在加载中*/
    public boolean isLoading(){
        return state == STATE_LOADING;
    }
    /** 是否加载完成(没有更多数据)*/
    public boolean isComplete(){
        return state == STATE_COMPLETE;
    }

    /** 显示加载中*/
    public void showLoading(){
        state = STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /** 显示加载完成(没有更多数据时)*/
    public void showComplete(){
        state = STATE_COMPLETE;
        tvComplete.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /** 显示没有更多数据*/
    public void showError(String erroMsg){
        state = STATE_ERROR;
        tvError.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    public void setErrorClickListener(OnClickListener onClickListener){
        tvError.setOnClickListener(onClickListener);
    }
}
