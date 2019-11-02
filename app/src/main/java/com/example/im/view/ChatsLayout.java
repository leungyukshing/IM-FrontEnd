package com.example.im.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.im.ChatRoom;
import com.example.im.HttpSend;
import com.example.im.R;
import com.example.im.ResultCallbackListener;
import com.example.im.UserCenter;
import com.example.im.adapter.ChatsAdapter;
import com.example.im.adapter.UserItemAdapter;
import com.example.im.utils.UserItem;
import com.example.im.ImEntities;
import com.hdl.elog.ELog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ChatsLayout extends Fragment {
    private View root;
    private RecyclerView recyclerView;
    private Context context;
    private ChatsAdapter chatsAdapter;
    // public static List<UserItem> userItemList;
    private List<ImEntities.Chat> chatList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.layout_chats, container, false);
        initView();
        return root;
    }

    private void initView() {
        context = getContext();
        recyclerView = root.findViewById(R.id.chats_recyclerview);
        loadChatList();

        // set adapter
        chatsAdapter = new ChatsAdapter(context, chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatsAdapter);
        chatsAdapter.setmOnItemClickListener(new ChatsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtra("chatName", chatList.get(i).getChatname());
                intent.putExtra("chatID", chatList.get(i).getChatid());
                context.startActivity(intent);
            }
        });

        // Add ItemTouchHelper
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = viewHolder1.getAdapterPosition();

                if (fromPos < toPos) {
                    for (int i = fromPos; i < toPos; i++) {
                        Collections.swap(chatList, i, i + 1);
                    }
                }
                else {
                    for (int i = fromPos; i > toPos; i--) {
                        Collections.swap(chatList, i, i - 1);
                    }
                }
                // update Adapter
                chatsAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();
                chatList.remove(pos);
                chatsAdapter.notifyItemRemoved(pos);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadChatList() {
        // call to server
        HttpSend.getInstance().getChatListByUserID(UserCenter.getInstance().getUser().getUserid() + "", new ResultCallbackListener<ImEntities.GetChatListResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(ImEntities.GetChatListResponse getChatListResponse) {
                chatList = getChatListResponse.getChatList();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ELog.e("getChatListByUserID error: " + e.getMessage());
                Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() { }
        });
    }
}
