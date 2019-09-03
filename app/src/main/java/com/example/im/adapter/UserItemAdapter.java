package com.example.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im.ChatRoom;
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
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username;
        private TextView sign;

        public BaseViewHolder(View view) {
            super(view);

            avatar = view.findViewById(R.id.user_item_avatar);
            username = view.findViewById(R.id.user_item_username);
            sign = view.findViewById(R.id.user_item_sign);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // click one contact, jump to chat page
                    Intent intent = new Intent(context, ChatRoom.class);
                    intent.putExtra("username", username.getText().toString());
                    context.startActivity(intent);

                    UserItem userItem = new UserItem();
                    userItem.setSign(sign.getText().toString());
                    userItem.setIconID((Integer)avatar.getTag());
                    userItem.setUsername(username.getText().toString());

                    for (UserItem item: ChatsLayout.userItemList) {
                        if (item.getUsername().equals(userItem.getUsername())) {
                            // the current user is in chats list
                            return;
                        }
                    }

                    ChatsLayout.userItemList.add(userItem);
                    UserItem.userItemList.add(userItem);
                }
            });
        }
    }
}
