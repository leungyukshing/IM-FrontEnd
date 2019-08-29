package com.example.im.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.BaseViewHolder> {

    @NonNull
    @Override
    public AvatarAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        BaseViewHolder(View view) {
            super(view);
            // imageView = view.findViewById(R.id.)
        }
    }
}
