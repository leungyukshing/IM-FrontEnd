package com.example.im.utils;

import java.util.ArrayList;
import java.util.List;

public class ChatItem {
    private boolean myInfo;
    private int iconID;
    private int chatID;
    private int senderID;
    private String senderName;
    private String content;
    private String chatObj;
    public static List<ChatItem> chatItemList = new ArrayList<>();

    public boolean isMyInfo() { return myInfo; }

    public void setMyInfo(boolean myInfo) { this.myInfo = myInfo; }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public int getIconID() {
        return iconID;
    }

    public void setChatID(int chatID) { this.chatID = chatID; }

    public int getChatID() { return chatID; }

    public void setSenderID(int senderID) { this.senderID = senderID; }

    public int getSenderID() { return senderID; }

    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderName() { return senderName; }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getChatObj() { return chatObj; }

    public void setChatObj(String chatObj) { this.chatObj = chatObj; }
}