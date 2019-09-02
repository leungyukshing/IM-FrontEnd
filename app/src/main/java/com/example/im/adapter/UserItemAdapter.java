package com.example.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.im.R;
import com.example.im.utils.UserItem;

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
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View view) {
            super(view);
        }
    }
}
