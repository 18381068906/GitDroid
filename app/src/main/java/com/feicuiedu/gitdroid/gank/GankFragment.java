package com.feicuiedu.gitdroid.gank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.gank.model.GankItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DELL on 2016/8/5.
 */
public class GankFragment extends Fragment implements GankPresenter.GankView {
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.content)
    ListView content;
    @BindView(R.id.emptyView)
    FrameLayout emptyView;
    @BindView(R.id.btnFilter)ImageButton btnFilter;

    private Date date;
    private ActivityUtils activityUtils;
    private Unbinder unbinder;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private GankAdapter gankAdapter;
    private GankPresenter gankPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(getActivity());
        calendar = Calendar.getInstance(Locale.CHINA);
        date = new Date(System.currentTimeMillis());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        gankPresenter = new GankPresenter(this);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        tvDate.setText(simpleDateFormat.format(date));
        gankAdapter = new GankAdapter();
        content.setAdapter(gankAdapter);
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GankItem gankItem = gankAdapter.getItem(position);
                // 跳至浏览器浏览此url
                activityUtils.startBrowser(gankItem.getUrl());
            }
        });
        // 初始获取数据(今天)
        gankPresenter.getGank(date);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(emptyView);
    }

    @Override
    public void hideEmptyView() {
        content.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(List<GankItem> gankItems) {
        gankAdapter.addAll(gankItems);
        gankAdapter.notifyDataSetChanged();
    }
    @OnClick(R.id.btnFilter)
    public void showDateDialog() {
        int year = calendar.get(Calendar.YEAR);
        int monty = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                year, monty, day
        );
        datePickerDialog.show();
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // 更新日期，重新执行业务
            calendar.set(year, monthOfYear, dayOfMonth);
            date = calendar.getTime();
            tvDate.setText(simpleDateFormat.format(date));
            gankPresenter.getGank(date);
        }
    };
}
