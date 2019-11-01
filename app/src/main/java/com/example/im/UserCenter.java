package com.example.im;

public class UserCenter {
    private static UserCenter userCenter;
    private  String userName;
    private int userID;

    private UserCenter() { }

    public static UserCenter getInstance() {
        if (userCenter == null) {
            synchronized (HttpSend.class) {
                if (userCenter == null) {
                    userCenter = new UserCenter();
                }
            }
        }
        return userCenter;
    }

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

}
