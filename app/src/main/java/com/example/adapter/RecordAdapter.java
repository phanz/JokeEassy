package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jokeeassy.R;
import com.example.model.Group;
import com.example.model.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/7/8.
 */

public class RecordAdapter extends BaseAdapter {
    private static final String TAG = "RecordAdapter";

    private List<Record> mRecordList;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecordAdapter(Context context){
        mContext = context;
        mRecordList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    public void addRecords(List<Record> recordList){
        mRecordList.addAll(0,recordList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordHolder holder = null;
        if(view == null){
            view = mInflater.inflate(R.layout.item_common,viewGroup,false);
            ImageView avatarImage = (ImageView) view.findViewById(R.id.user_avatar_image);
            TextView userNameText = (TextView) view.findViewById(R.id.user_name_text);
            TextView contentText = (TextView) view.findViewById(R.id.content_text);
            TextView categoryText = (TextView) view.findViewById(R.id.category_text);
            TextView diggCountText = (TextView) view.findViewById(R.id.digg_count_text);
            TextView buryCountText = (TextView) view.findViewById(R.id.bury_count_text);
            TextView commentCountText = (TextView) view.findViewById(R.id.comment_count_text);
            holder = new RecordHolder();
            holder.avatarImage = avatarImage;
            holder.userNameText = userNameText;
            holder.contentText = contentText;
            holder.categoryText = categoryText;
            holder.diggCountText = diggCountText;
            holder.buryCountText = buryCountText;
            holder.commentCountText = commentCountText;
            view.setTag(holder);

        }else{
            holder = (RecordHolder) view.getTag();
        }
        Record record = mRecordList.get(i);
        Group group = record.getGroup();
        if(group == null){
            Log.e(TAG,"Group为空：" + record.toString());
        }else{
            holder.avatarImage.setImageResource(R.drawable.default_round_head);
            holder.userNameText.setText(group.getUser().getName());
            holder.contentText.setText(group.getContent());
            holder.categoryText.setText(group.getCategoryName());
            holder.diggCountText.setText(group.getDiggCount() + "");
            holder.buryCountText.setText(group.getBuryCount() + "");
            holder.commentCountText.setText(group.getCommentCount() + "");
        }

        return view;
    }

    private static class RecordHolder{
        private ImageView avatarImage;
        private TextView userNameText;
        private TextView contentText;
        private TextView categoryText;
        private TextView diggCountText;
        private TextView buryCountText;
        private TextView commentCountText;
    }
}
