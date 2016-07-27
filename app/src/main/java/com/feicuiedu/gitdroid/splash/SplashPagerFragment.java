package com.feicuiedu.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.splash.pager.Pager2;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by DELL on 2016/7/26.
 */
public class SplashPagerFragment extends Fragment {
    private ViewPager viewPager;
    private SplashPagerAdapter adapter;
    private CircleIndicator circleIndicator;
    private FrameLayout layoutPhone,content;
    private ImageView ivPhoneFont;
    private int red,yellow,green;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        layoutPhone = (FrameLayout) view.findViewById(R.id.layoutPhone);
        ivPhoneFont = (ImageView) view.findViewById(R.id.ivPhoneFont);
        content = (FrameLayout) view.findViewById(R.id.content);
        red = getResources().getColor(R.color.colorRed);
        yellow = getResources().getColor(R.color.colorYellow);
        green = getResources().getColor(R.color.colorGreen);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(colorListener);
        viewPager.addOnPageChangeListener(phoneListener);
    }
    private ViewPager.OnPageChangeListener colorListener = new ViewPager.OnPageChangeListener() {
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0){
                int color = (int) argbEvaluator.evaluate(positionOffset,green,red);
                content.setBackgroundColor(color);
                return;
            }
            if (position == 1){
                int color = (int) argbEvaluator.evaluate(positionOffset,red,yellow);
                content.setBackgroundColor(color);
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private ViewPager.OnPageChangeListener phoneListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0){
                ivPhoneFont.setAlpha(positionOffset);
                float scale = 0.3f + positionOffset*0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);
                int trans = (int) ((positionOffset-1)*240);
                layoutPhone.setTranslationX(trans);
                return;
            }
            if (position == 1){
                layoutPhone.setTranslationX(-positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (position==2){
                Pager2 pager2 = (Pager2) adapter.getView(position);
                pager2.showImage();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
