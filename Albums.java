package com.example.sakthivel.retrofitapp;

import com.google.gson.annotations.SerializedName;

public class Albums {

    @SerializedName("userId")
    private String userId;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;

    public Albums(String userId, String id, String title){
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
