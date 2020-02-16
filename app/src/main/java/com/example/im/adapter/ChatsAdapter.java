package com.example.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.im.R;
import com.example.im.ImEntities;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.BaseViewHolder> {
    private Context context;
    private List<ImEntities.Chat> chatList;

    public ChatsAdapter(Context context, List<ImEntities.Chat> list) {
        this.context = context;
        this.chatList = list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder baseViewHolder, int i) {
        final ImEntities.Chat chat = chatList.get(i);
        baseViewHolder.chatName.setText(chat.getChatname());
        // baseViewHolder.sign.setText(chat.getSign());
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

    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return chatList == null ? 0 : chatList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView chatName;
        private TextView sign;
        private LinearLayout linearLayout;

        public BaseViewHolder(View view) {
            super(view);

            avatar = view.findViewById(R.id.chat_item_avatar);
            chatName = view.findViewById(R.id.chat_item_chatname);
            sign = view.findViewById(R.id.chat_item_sign);
            linearLayout = view.findViewById(R.id.chat_item_layout);
        }
    }
}
