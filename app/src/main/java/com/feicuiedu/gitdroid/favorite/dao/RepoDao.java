package com.feicuiedu.gitdroid.favorite.dao;

import android.content.Context;

import com.feicuiedu.gitdroid.favorite.model.RepoGroupTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DELL on 2016/8/3.
 */
public class RepoDao {
    private Context context;
    private DBHelper dbHelper;
    private Dao<RepoGroupTable,Long> dao;
    public RepoDao(Context context) {
        if (dbHelper==null){
            dbHelper = DBHelper.getInstance(context);
        }
        try {
            dao = dbHelper.getDao(RepoGroupTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.context = context;
        list = getDefaultGroups();
        creatOrUpdata(list);
    }
    //添加或更新表
    public void creatOrUpdata(RepoGroupTable table){
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //添加或更新表
    public void creatOrUpdata(List<RepoGroupTable> tables){
        for (RepoGroupTable table :tables){
            creatOrUpdata(table);
        }
    }
    //根据ID查询
    public RepoGroupTable findByID(Long id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //查询全部
    public List<RepoGroupTable> findAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<RepoGroupTable> list;
    public List<RepoGroupTable> getDefaultGroups(){
        if (list!=null) {
            return list;
        }
        try {
            InputStream is = context.getAssets().open("repogroup.json");
            String content = IOUtils.toString(is);
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<List<RepoGroupTable>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
