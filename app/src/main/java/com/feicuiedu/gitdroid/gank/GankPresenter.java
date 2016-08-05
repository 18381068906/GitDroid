package com.feicuiedu.gitdroid.gank;

import android.support.annotation.NonNull;

import com.feicuiedu.gitdroid.gank.model.GankItem;
import com.feicuiedu.gitdroid.gank.model.GankResult;
import com.feicuiedu.gitdroid.gank.network.GankClient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 2016/8/5.
 */
public class GankPresenter {
    private Call<GankResult> gankResultCall;
    private GankView gankView;
    public GankPresenter(@NonNull GankView gankView){
        this.gankView  =  gankView;
    }
    public void getGank(Date date){
        if (gankResultCall!=null){
            gankResultCall.cancel();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        gankResultCall = GankClient.getInstance().getDailyData(year, month, day);
        gankResultCall.enqueue(gankResultCallback);
    }
    private Callback<GankResult> gankResultCallback = new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            GankResult gankResult = response.body();
            if (gankResult == null) {
                gankView.showMessage("未知错误!");
                return;
            }
            // 没有数据的情况
            if (gankResult.isError()
                    || gankResult.getResults() == null
                    || gankResult.getResults().getAndroidItems() == null
                    || gankResult.getResults().getAndroidItems().isEmpty()) {
                gankView.showEmptyView();
                return;
            }
            List<GankItem> gankItems = gankResult.getResults().getAndroidItems();
            gankView.hideEmptyView();
            gankView.setData(gankItems);
        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {

        }
    };
    interface GankView{
        void showEmptyView();

        void hideEmptyView();

        void showMessage(String msg);

        void setData(List<GankItem> gankItems);
    }
}
