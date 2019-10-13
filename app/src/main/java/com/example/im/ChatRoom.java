package com.example.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.im.adapter.ChatItemAdapter;
import com.example.im.utils.ChatItem;
import com.example.im.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private TitleBar titleBar;
    private ListView listView;
    private Button sendBtn;
    private EditText msg;
    public static List<ChatItem> chatItemList = new ArrayList<>();
    public static ChatItemAdapter chatItemAdapter;
    private String chatObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    private void initView() {

    }
}
