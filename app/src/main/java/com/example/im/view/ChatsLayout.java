package com.example.im.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.im.R;
import com.example.im.adapter.UserItemAdapter;
import com.example.im.utils.UserItem;

import java.util.ArrayList;
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
    }
}
