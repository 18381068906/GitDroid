package com.feicuiedu.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.favorite.dao.LocalRepoDao;
import com.feicuiedu.gitdroid.favorite.dao.RepoDao;
import com.feicuiedu.gitdroid.favorite.model.LocalRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoGroupTable;

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
    private LocalRepo localRepo;
    private int groupID;
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
        registerForContextMenu(listView);
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
        groupID = item.getItemId();
        setData();
        return true;
    }

    private void setData() {
        switch (groupID){
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
                adapter.addAll(localRepoDao.queryForGroupId(groupID));
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo menuInfo1 = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = menuInfo1.position;
        localRepo = adapter.getItem(position);
        if (v.getId() == R.id.listView){
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite,menu);
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            List<RepoGroupTable> mlist = repoDao.findAll();
            for (RepoGroupTable table :mlist){
                subMenu.add(R.id.menu_group_move,table.getId(),Menu.NONE,table.getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        RepoGroupTable mTable = repoDao.findByID((long) id);
        if (item.getItemId()==R.id.delete){
            localRepoDao.delete(localRepo);
            setData();
            return true;
        }
        if (item.getGroupId() == R.id.menu_group_move){
            switch (item.getItemId()){
                case R.id.repo_group_no:
                    localRepo.setRepoGroup(null);
                    break;
                default:
                    localRepo.setRepoGroup(mTable);
                    break;
            }
            localRepoDao.creatOrUpdata(localRepo);
            setData();
        }

        return super.onContextItemSelected(item);
    }
}
