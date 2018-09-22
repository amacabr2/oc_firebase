package com.antho.firebaseoc.models;

import android.support.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    private boolean isMentor;
    @Nullable private String urlPicture;

    public User() {
    }

    public User(String uid, String username, @Nullable String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.isMentor = false;
        this.urlPicture = urlPicture;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsMentor() {
        return isMentor;
    }

    public void setMentor(boolean mentor) {
        isMentor = mentor;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
