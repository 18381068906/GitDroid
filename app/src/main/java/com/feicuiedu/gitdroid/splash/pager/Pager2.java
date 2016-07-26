package com.feicuiedu.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

/**
 * Created by DELL on 2016/7/26.
 */
public class Pager2 extends FrameLayout {

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2,this,true);
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pager2(Context context) {
        this(context, null);
    }
}
