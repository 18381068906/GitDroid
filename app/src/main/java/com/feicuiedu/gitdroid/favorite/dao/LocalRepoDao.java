package com.feicuiedu.gitdroid.favorite.dao;

import android.content.Context;

import com.feicuiedu.gitdroid.favorite.model.LocalRepo;
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
public class LocalRepoDao {
    private Context context;
    private DBHelper dbHelper;
    private Dao<LocalRepo,Long> dao;
    private List<LocalRepo> localRepos;
    public LocalRepoDao(Context context) {
        if (dbHelper==null){
            dbHelper = DBHelper.getInstance(context);
        }
        try {
            dao = dbHelper.getDao(LocalRepo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.context = context;
        if (queryForAll()==null){
            localRepos = getDefaultGroups();
            creatOrUpdata(localRepos);
        }

    }
    //添加或更新表
    public void creatOrUpdata(LocalRepo table){
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //添加或更新表
    public void creatOrUpdata(List<LocalRepo> tables){
        for (LocalRepo table :tables){
            creatOrUpdata(table);
        }
    }
    /**
     * 删除本地仓库数据
     */
    public void delete(LocalRepo localRepo){
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库(图像处理的，架构的...,能查到全部或未分类的？？？)
     *
     * @param groupId 类别ID号
     * @return 仓库列表
     */
    public List<LocalRepo> queryForGroupId(int groupId){
        try {
            return dao.queryForEq(LocalRepo.COLUMN_GROUP_ID, groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 查询本地仓库(未分类的)
     */
    public List<LocalRepo> queryForNoGroup(){
        try {
            return dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询本地仓库(全部的)
     */
    public List<LocalRepo> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public LocalRepo queryForID(Long id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //添加默认数据
    public List<LocalRepo> getDefaultGroups(){
        if (localRepos!=null) {
            return localRepos;
        }
        try {
            InputStream is = context.getAssets().open("defaultrepos.json");
            String content = IOUtils.toString(is);
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<List<LocalRepo>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
