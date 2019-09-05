package com.example.im.view;

import android.content.Context;
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

import com.example.im.R;
import com.example.im.adapter.UserItemAdapter;
import com.example.im.utils.UserItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatsLayout extends Fragment {
    private View root;
    private RecyclerView recyclerView;
    private Context context;
    private UserItemAdapter userItemAdapter;
    public static List<UserItem> userItemList;

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
        userItemList = new ArrayList<>();

        // set adapter
        userItemAdapter = new UserItemAdapter(context, userItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userItemAdapter);

        // Add ItemTouchHelper
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = viewHolder1.getAdapterPosition();

                if (fromPos < toPos) {
                    for (int i = fromPos; i < toPos; i++) {
                        Collections.swap(userItemList, i, i + 1);
                    }
                }
                else {
                    for (int i = fromPos; i > toPos; i--) {
                        Collections.swap(userItemList, i, i - 1);
                    }
                }
                // update Adapter
                userItemAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();
                userItemList.remove(pos);
                userItemAdapter.notifyItemRemoved(pos);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        for (UserItem item: UserItem.userItemList) {
            userItemList.add(item);
        }
    }
}
