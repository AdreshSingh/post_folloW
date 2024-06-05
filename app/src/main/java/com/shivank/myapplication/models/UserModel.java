package com.shivank.myapplication.models;

import java.util.List;

public class UserModel {
    public UserModel(){}

    public UserModel(String userName, String userEmail, String userPassword, long timeStamp) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.timeStamp = timeStamp;
    }

    public UserModel(String userId, String userName, String userEmail, String userPassword, long timeStamp, List<String> following) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.timeStamp = timeStamp;
        this.following = following;
    }
    public UserModel(List<String> following) {
        this.following = following;
    }
    public UserModel(String userName,String userEmail, String userPassword, long timeStamp,List<String> following) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.timeStamp = timeStamp;
        this.following = following;
    }
    String userId;
    String userName;
    String userEmail;
    String userPassword;
    long timeStamp;
    List<String> following;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFollowing() {
        return following;
    }
    public void setFollowing(List<String> following) {
        this.following = following;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UserModel(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
