package com.example.im.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.adapter.MomentItemAdapter;
import com.example.im.utils.MomentItem;

import java.util.ArrayList;
import java.util.List;

public class MomentsLayout extends Fragment {
    private View root;
    private List<MomentItem> momentItemList;
    private RecyclerView momentRecyclerView;
    private EditText addText;
    private Button sendBtn;
    private MomentItemAdapter momentItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.layout_moments, container, false);
        initView();
        return root;
    }

    private void initView() {
        momentRecyclerView = root.findViewById(R.id.moments_recyclerview);
        addText = root.findViewById(R.id.moments_addText);
        sendBtn = root.findViewById(R.id.moments_sendBtn);

        momentItemList = new ArrayList<>();
        momentItemList.clear();
        for (MomentItem momentItem: MomentItem.momentItemList) {
            momentItemList.add(momentItem);
        }

        // Set Adapter
        momentItemAdapter = new MomentItemAdapter(getContext(), momentItemList);
        momentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        momentRecyclerView.setAdapter(momentItemAdapter);

        // Set Listener
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MomentItem momentItem = new MomentItem();
                // TODO: add user info and save in DB
                addText.setText("");

                momentItemList.add(momentItem);
                MomentItem.momentItemList.add(momentItem);
            }
        });
    }
}
