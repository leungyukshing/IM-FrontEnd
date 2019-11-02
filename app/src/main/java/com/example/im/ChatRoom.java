package com.example.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private String chatName;
    private int chatID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.chatroom_titleBar);
        listView =  findViewById(R.id.chatroom_listView);
        msg = findViewById(R.id.chatroom_message);
        sendBtn = findViewById(R.id.chatroom_sendBtn);
        chatName = getIntent().getStringExtra("chatName");
        chatID = getIntent().getIntExtra("chatID", -1);
        titleBar.SetTitleText(chatName);
        loadChatMsg();

        chatItemAdapter = new ChatItemAdapter(ChatRoom.this, chatItemList, R.layout.chat_other);
        listView.setAdapter(chatItemAdapter);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = msg.getText().toString();
                if (!content.isEmpty()) {
                    ChatItem chatItem = new ChatItem();
                    chatItem.setContent(content);
                    chatItem.setSenderID(Integer.parseInt(UserCenter.getInstance().getUser().getUserid()));
                    chatItem.setSenderName(UserCenter.getInstance().getUser().getUsername());
                    chatItem.setMyInfo(true);
                    chatItem.setChatID(chatID);
                    // 1. save to DB
                    // 2. send to Server
                        if (send(chatItem)) {
                            // reset
                            msg.setText("");
                        }
                        else {
                            Toast.makeText(ChatRoom.this, "Send Failed", Toast.LENGTH_SHORT).show();
                        }
                }
                else {
                    Toast.makeText(ChatRoom.this, "Cannot Send Empty Message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        titleBar.SetTitleBarClickListener(new TitleBar.TitleBarOnClickListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }

            @Override
            public void rightButtonClick() { }
        });
    }

    private boolean send(ChatItem chatItem) {
        return DataBaseHelper.getInstance(getApplicationContext()).sendMessage(chatItem);
    }

    private void loadChatMsg() {
        this.chatItemList = DataBaseHelper.getInstance(getApplicationContext()).queryWithChatID(chatID + "");
    }
}
