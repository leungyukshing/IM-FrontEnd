package com.example.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.utils.ChatItem;
import com.example.im.utils.ImageManager;

import org.w3c.dom.Text;

import java.util.List;

public class ChatItemAdapter extends ArrayAdapter<ChatItem> {
    private LayoutInflater inflater;
    private List<ChatItem> chatItemList;
    public ChatItemAdapter(Context context, List<ChatItem> chatItemList, int resource) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.chatItemList = chatItemList;
    }

    @Override
    public int getCount() {
        return chatItemList == null ? 0 : chatItemList.size();
    }

    @Nullable
    @Override
    public ChatItem getItem(int position) {
        return chatItemList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatItem chatItem = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            if (!chatItem.isMyInfo()) {
                view = inflater.inflate(R.layout.chat_other, parent, false);
            }
            else {
                view = inflater.inflate(R.layout.chat_me, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.icon = view.findViewById(R.id.chat_icon);
            viewHolder.username = view.findViewById(R.id.chat_username);
            viewHolder.content = view.findViewById(R.id.chat_content);

            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.icon.setImageResource(ImageManager.imagesAvatar[chatItemList.get(position).getIconID()]);
        viewHolder.username.setText(chatItem.isMyInfo() ? chatItemList.get(position).getSenderName() : chatItemList.get(position).getChatObj());
        viewHolder.content.setText(chatItemList.get(position).getContent());
        return view;
    }

    class ViewHolder {
        ImageView icon;
        TextView username;
        TextView content;
    }
}
