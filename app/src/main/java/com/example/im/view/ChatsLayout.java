package com.example.im.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.im.adapter.UserItemAdapter;
import com.example.im.utils.UserItem;

import java.util.List;

public class ChatsLayout extends Fragment {
    private View root;
    private RecyclerView recyclerView;
    private static List<UserItem> userItemList;
    private Context context;
    private UserItemAdapter userItemAdapter;
}
