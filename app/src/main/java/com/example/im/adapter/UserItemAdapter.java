package com.example.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im.ChatRoom;
import com.example.im.ImEntities;
import com.example.im.R;
import com.example.im.utils.UserItem;
import com.example.im.view.ChatsLayout;

import java.util.List;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.BaseViewHolder> {
    private Context context;
    private List<UserItem> userItemList;

    public UserItemAdapter(Context context, List<UserItem> list) {
        this.context = context;
        this.userItemList = list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder baseViewHolder, int i) {
        final UserItem userItem = userItemList.get(i);
        baseViewHolder.userName.setText(userItem.getUsername());
        baseViewHolder.sign.setText(userItem.getSign());
        baseViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(baseViewHolder.getAdapterPosition());
            }
        });
    }

    public interface onItemClickListener {
        void onItemClick(int i);
    }

    private UserItemAdapter.onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(UserItemAdapter.onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return userItemList == null ? 0 : userItemList.size();
    }

    public void setmDatas(List<UserItem> datas) {
        if (userItemList != null) {
            userItemList.clear();
            this.notifyDataSetChanged();
        }
        userItemList.addAll(datas);
        this.notifyDataSetChanged();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView userName;
        private TextView sign;
        private LinearLayout linearLayout;

        public BaseViewHolder(View view) {
            super(view);

            avatar = view.findViewById(R.id.user_item_avatar);
            userName = view.findViewById(R.id.user_item_username);
            sign = view.findViewById(R.id.user_item_sign);
            linearLayout = view.findViewById(R.id.user_item_layout);
        }
    }
}
