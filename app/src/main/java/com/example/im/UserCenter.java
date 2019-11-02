package com.example.im;

public class UserCenter {
    private static UserCenter userCenter;
    private ImEntities.User user;

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

    public void setUser(ImEntities.User user) { this.user = user; }

    public ImEntities.User getUser() { return user; }
}
