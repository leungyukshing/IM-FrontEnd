package com.example.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.utils.Image;
import com.example.im.utils.ImageManager;
import com.example.im.utils.MomentItem;

import java.util.List;

public class MomentItemAdapter extends RecyclerView.Adapter<MomentItemAdapter.BaseViewHolder> {
    private Context context;
    private List<MomentItem> momentItemList;

    public MomentItemAdapter(Context context, List<MomentItem> momentItems) {
        this.context = context;
        this.momentItemList = momentItems;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.moment_item, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.avatar.setImageResource(ImageManager.imagesAvatar[momentItemList.get(i).getIconID()]);
        baseViewHolder.username.setText(momentItemList.get(i).getUsername());
        baseViewHolder.content.setText(momentItemList.get(i).getContent());
        baseViewHolder.likes.setImageResource(momentItemList.get(i).getLikes() ? R.drawable.ungood: R.drawable.good); // ???
    }

    @Override
    public int getItemCount() {
        return momentItemList == null ? 0: momentItemList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username;
        private TextView content;
        private ImageView likes;
        private boolean isLiked = false;

        public BaseViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.moment_avatar);
            username = view.findViewById(R.id.moment_username);
            content = view.findViewById(R.id.moment_content);
            likes = view.findViewById(R.id.moment_likes);

            likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isLiked = !isLiked;
                    likes.setImageResource(isLiked? R.drawable.ungood: R.drawable.good);
                    for (MomentItem momentItem: MomentItem.momentItemList) { // Use Text Content to indentify a moment. TODO: use momentID
                        if (momentItem.getContent().equals(content.getText().toString())) {
                            momentItem.setLikes(isLiked);
                        }
                    }
                }
            });
        }
    }
}
