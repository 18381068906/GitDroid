package com.feicuiedu.gitdroid.hotuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 2016/8/2.
 */
public class UserListAdapter extends BaseAdapter {
    private ArrayList<UserList> data ;
    public UserListAdapter() {
        data = new ArrayList<UserList>();
    }

    public void addAll(Collection<UserList> userLists){
        data.addAll(userLists);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return data.size();
    }

    @Override public UserList getItem(int position) {
        return data.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_user, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        UserList user = getItem(position); // 当前item选的"数据"
        viewHolder.tvRepoName.setText(user.getLogin());
        ImageLoader.getInstance().displayImage(user.getAvatar_url(), viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvRepoName)
        TextView tvRepoName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
