package com.feicuiedu.gitdroid.favorite.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by DELL on 2016/8/3.
 */
@DatabaseTable(tableName = "repo")
public class RepoGroupTable {
    @SerializedName("id")
    @DatabaseField(id = true)
    private int id;
    @SerializedName("name")
    @DatabaseField(columnName = "NAME")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
