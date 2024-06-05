package com.shivank.myapplication.models;

import java.util.Date;

public class PostModel {

    public PostModel(){}
    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {
        this.postUserId = postUserId;
    }

    String postUserId;
    String postUserName;
    String postTitle;
    String postInfo;
    long timeStamp;

    public PostModel(String postUserId, String postUserName, String postTitle, String postInfo, long timeStamp) {
        this.postUserId = postUserId;
        this.postUserName = postUserName;
        this.postTitle = postTitle;
        this.postInfo = postInfo;
        this.timeStamp = timeStamp;
    }


    public PostModel(String postUserId, String postTitle, String postInfo, long timeStamp) {
        this.postUserId = postUserId;
        this.postTitle = postTitle;
        this.postInfo = postInfo;
        this.timeStamp = timeStamp;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }



}
