package com.feicuiedu.gitdroid.favorite.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by DELL on 2016/8/3.
 */
@DatabaseTable(tableName = "Local_Repo")
public class LocalRepo {
    public static final String COLUMN_GROUP_ID = "group_id";
    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;

    @DatabaseField
    private String description;

    @DatabaseField(columnName = "start_count")
    @SerializedName("stargazers_count")
    private int startCount;

    @DatabaseField(columnName = "fork_count")
    @SerializedName("forks_count")
    private int forkCount;

    @DatabaseField(columnName = "avatar_url")
    @SerializedName("avatar_url")
    private String avatar;

    // 是一个外键,可以为null
    @DatabaseField(columnName = COLUMN_GROUP_ID,foreign = true,canBeNull = true)
    @SerializedName("group")
    private RepoGroupTable repoGroup;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRepoGroup(RepoGroupTable repoGroup) {
        this.repoGroup = repoGroup;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStartCount() {
        return startCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public RepoGroupTable getRepoGroup() {
        return repoGroup;
    }
}
