package com.feicuiedu.gitdroid.hotuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.components.FooterView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by DELL on 2016/8/2.
 */
public class HotUserFragment extends Fragment implements HotUserPresenter.HotUserView {
    @BindView(R.id.lvRepos)ListView lvRepos;
    @BindView(R.id.emptyView)TextView emptyView;
    @BindView(R.id.errorView)TextView errorView;
    @BindView(R.id.ptrClassicFrameLayout)PtrClassicFrameLayout ptrFrameLayout;
    private HotUserPresenter presenter;
    private UserListAdapter adapter;
    private FooterView footerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_user,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new HotUserPresenter(this);
        adapter = new UserListAdapter();
        lvRepos.setAdapter(adapter);
        lvRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        initHead();
        initFoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.refresh();
    }

    private void initFoot() {
        footerView = new FooterView(getContext());
        Mugen.with(lvRepos, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }

            @Override
            public boolean isLoading() {
                return lvRepos.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return lvRepos.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    private void initHead() {
        //设置刷新间隔冲突
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrFrameLayout.setDurationToCloseHeader(1500);
        // 下拉刷新监听处理
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                // 在工具类中加载数据，加载完成后回调本类中的方法刷新视图
                presenter.refresh();
            }
        });
        // 不影响程序。只完善视图
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + " JAVA");
        header.setPadding(0, 60, 0, 60);

        // 修改Ptr的HeaderView效果
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }
    @Override
    public void refresh(List<UserList> userList) {
        adapter.clear();
        adapter.addAll(userList);
    }

    @Override
    public void loadMore(List<UserList> userList) {
        adapter.addAll(userList);
    }

    @Override
    public void stopRefresh() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void stopLoad() {
        lvRepos.removeFooterView(footerView);
    }

    @Override
    public void showLoadMore() {
        if (lvRepos.getFooterViewsCount() == 0) {
            lvRepos.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void showToast(String msg) {
        if(getContext()!=null){
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }

    }
}
