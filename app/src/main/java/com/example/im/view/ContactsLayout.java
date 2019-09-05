package com.example.im.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ContactsLayout extends Fragment {
    private View root;
    private Context context;
    private List<UserItem> groupList; // Group Chats List
    private List<UserItem> contactList; // Friends List
    private PictureAndTextButton group_patb;
    private PictureAndTextButton contact_patb;
    private RecyclerView group_recyclerview;
    private RecyclerView contact_recyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.layout_contacts, container, false);
        initView();
        return root;
    }

    private void initView() {
        group_patb = root.findViewById(R.id.groups_patb);
        contact_patb = root.findViewById(R.id.contacts_patb);
        group_recyclerview = root.findViewById(R.id.groups_recyclerview);
        contact_recyclerview = root.findViewById(R.id.contacts_recyclerview);

        groupList = new ArrayList<>();
        contactList = new ArrayList<>();

        // Load Data to Lists


        // Set Group Adapter and Listener
        UserItemAdapter groupAdapter = new UserItemAdapter(context, groupList);
        group_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        group_recyclerview.setAdapter(groupAdapter);

        group_patb.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                if (group_recyclerview.getVisibility() == View.VISIBLE) {
                    group_recyclerview.setVisibility(View.GONE);
                    group_patb.setImageView(R.drawable.shink);
                }
                else {
                    group_recyclerview.setVisibility(View.VISIBLE);
                    group_patb.setImageView(R.drawable.rise);
                }
            }
        });


        // Set Contact Adapter and Listener
        UserItemAdapter contactAdapter = new UserItemAdapter(context, contactList);
        contact_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        contact_recyclerview.setAdapter(contactAdapter);

        contact_patb.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                if (contact_recyclerview.getVisibility() == View.VISIBLE) {
                    contact_recyclerview.setVisibility(View.GONE);
                    contact_patb.setImageView(R.drawable.shink);
                }
                else {
                    contact_recyclerview.setVisibility(View.VISIBLE);
                    contact_patb.setImageView(R.drawable.rise);
                }
            }
        });
    }
}
