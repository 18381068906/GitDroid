package com.feicuiedu.gitdroid.gank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.gank.model.GankItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by DELL on 2016/8/5.
 */
public class GankAdapter extends BaseAdapter {
    private ArrayList<GankItem> data ;
    public GankAdapter() {
        data = new ArrayList<GankItem>();
    }

    public void addAll(Collection<GankItem> gankItems){
        data.addAll(gankItems);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return data.size();
    }

    @Override public GankItem getItem(int position) {
        return data.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_gank, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        GankItem gankItem = getItem(position); // 当前item选的"数据"
        viewHolder.gank_item.setText(gankItem.getDesc());
//        viewHolder.tvRepoName.setText(gankItem.getFullName());
//        viewHolder.tvRepoInfo.setText(repo.getDescription());
//        viewHolder.tvRepoStars.setText("stars: "+repo.getStartCount() + "");
//        ImageLoader.getInstance().displayImage(repo.getAvatar(), viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder {
        public TextView gank_item;

        public ViewHolder(View view) {
            gank_item = (TextView) view.findViewById(R.id.gank_item);
        }
    }
}
