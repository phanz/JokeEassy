package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jokeeassy.R;
import com.example.model.Comment;
import com.example.model.Record;
import com.example.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/7/22.
 */

public class CommentAdapter extends BaseAdapter {
    private static final String TAG = "CommentAdapter";

    private List<Comment> mCommentList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context){
        mContext = context;
        mCommentList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCommentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setCommentList(List<Comment> commentList) {
        this.mCommentList = commentList;
    }

    public void addCommentList(List<Comment> commentList){
        mCommentList.addAll(commentList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = mInflater.inflate(R.layout.item_comment,viewGroup,false);
            ImageView avatarImage = (ImageView) view.findViewById(R.id.user_avatar_image);
            TextView userNameText = (TextView) view.findViewById(R.id.user_name_text);
            TextView commentTimeText = (TextView) view.findViewById(R.id.comment_time_text);
            TextView diggCountText = (TextView) view.findViewById(R.id.digg_count_text);
            ImageView diggImage = (ImageView) view.findViewById(R.id.comment_digg_image);
            TextView commentText = (TextView) view.findViewById(R.id.comment_text);

            holder = new ViewHolder();
            holder.avatarImage = avatarImage;
            holder.userNameText = userNameText;
            holder.commentTimeText = commentTimeText;
            holder.diggCountText = diggCountText;
            holder.diggImage = diggImage;
            holder.commentText = commentText;
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        Comment comment = mCommentList.get(i);
        Glide.with(mContext).load(comment.getAvatarUrl()).into(holder.avatarImage);
        holder.userNameText.setText(comment.getUserName());
        holder.commentTimeText.setText(DateUtils.getFormatDateTime(comment.getCreateTime(),"MM-dd HH:mm:ss"));
        holder.diggCountText.setText(comment.getDiggCount() + "");
        holder.commentText.setText(comment.getText());
        return view;
    }

    public static class ViewHolder{
        public ImageView avatarImage;
        public TextView userNameText;
        public TextView commentTimeText;
        public TextView diggCountText;
        public ImageView diggImage;
        public TextView commentText;
    }
}
