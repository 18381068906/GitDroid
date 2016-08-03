package com.feicuiedu.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.favorite.dao.LocalRepoDao;
import com.feicuiedu.gitdroid.favorite.dao.RepoDao;
import com.feicuiedu.gitdroid.favorite.model.LocalRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoGroupTable;
import com.feicuiedu.gitdroid.hotrepo.Repo;
import com.feicuiedu.gitdroid.repoinfo.RepoInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 2016/8/3.
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
    @BindView(R.id.listView)ListView listView;
    @BindView(R.id.tvGroupType)TextView tvGroupType;
    @BindView(R.id.btnFilter)ImageButton btnFilter;
    private RepoDao repoDao;
    private LocalRepoAdapter adapter;
    private LocalRepoDao localRepoDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new LocalRepoAdapter();
        listView.setAdapter(adapter);;
        localRepoDao = new LocalRepoDao(getContext());
        repoDao = new RepoDao(getContext());
        adapter.addAll(localRepoDao.queryForAll());
    }

    @OnClick(R.id.btnFilter)
    public void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        Menu menu = popupMenu.getMenu();
        List<RepoGroupTable> list = repoDao.findAll();
        for (RepoGroupTable groupTable : list){
            menu.add(Menu.NONE,groupTable.getId(),Menu.NONE,groupTable.getName());
        }
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String text = item.getTitle().toString();
        tvGroupType.setText(text);
        setData(item);
        return true;
    }

    private void setData(MenuItem item) {
        switch (item.getItemId()){
            case R.id.repo_group_all:
                adapter.clear();
                adapter.addAll(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.clear();
                adapter.addAll(localRepoDao.queryForNoGroup());
                break;
            default:
                adapter.clear();
                adapter.addAll(localRepoDao.queryForGroupId(item.getItemId()));
                break;
        }
    }
}
