package com.feicuiedu.gitdroid.hotuser;

import com.feicuiedu.gitdroid.hotrepo.Repo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2016/8/2.
 */
public class UserResult {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<UserList> userList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<UserList> getUserList() {
        return userList;
    }
}
