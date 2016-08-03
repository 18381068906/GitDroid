package com.feicuiedu.gitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicuiedu.gitdroid.favorite.model.LocalRepo;
import com.feicuiedu.gitdroid.favorite.model.RepoGroupTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by DELL on 2016/8/3.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    Context context;
    private DBHelper(Context context) {
        super(context, "repo_favorite.db", null, 1);
        this.context = context;
    }
    private static DBHelper dbHelper;
    public static synchronized DBHelper getInstance(Context c){
        if (dbHelper==null){
            dbHelper = new DBHelper(c);
        }
        return dbHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, RepoGroupTable.class);
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,RepoGroupTable.class, true);
            TableUtils.dropTable(connectionSource,LocalRepo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
