package com.example.im.utils;

import java.util.ArrayList;
import java.util.List;

public class UserItem {
    private int iconID;
    private String username;
    private String userid;
    private String sign;
    private String email;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) { this.sign = sign; }

    public String getUserid() { return userid; }

    public void setUserid(String userid) { this.userid = userid; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
