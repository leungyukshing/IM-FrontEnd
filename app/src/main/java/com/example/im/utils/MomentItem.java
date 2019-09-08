package com.example.im.utils;

import java.util.ArrayList;
import java.util.List;

public class MomentItem {
    private int iconID;
    private String username;
    private String content;
    boolean likes;
    public static List<MomentItem> momentItemList = new ArrayList<>();

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }


}
