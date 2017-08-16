package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public void setCommentList(List<Comment> commentList) {
        this.mCommentList = commentList;
        notifyDataSetChanged();
    }

    public void addCommentList(List<Comment> commentList){
        mCommentList.addAll(commentList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comment,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        Glide.with(mContext).load(comment.getAvatarUrl()).into(holder.avatarImage);
        holder.userNameText.setText(comment.getUserName());
        holder.commentTimeText.setText(DateUtils.getFormatDateTime(comment.getCreateTime(),"MM-dd HH:mm:ss"));
        holder.diggCountText.setText(comment.getDiggCount() + "");
        holder.commentText.setText(comment.getText());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatarImage;
        public TextView userNameText;
        public TextView commentTimeText;
        public TextView diggCountText;
        public ImageView diggImage;
        public TextView commentText;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImage = (ImageView) itemView.findViewById(R.id.user_avatar_image);
            userNameText = (TextView) itemView.findViewById(R.id.user_name_text);
            commentTimeText = (TextView) itemView.findViewById(R.id.comment_time_text);
            diggCountText = (TextView) itemView.findViewById(R.id.digg_count_text);
            diggImage = (ImageView) itemView.findViewById(R.id.comment_digg_image);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
