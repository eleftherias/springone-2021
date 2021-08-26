package com.example.springone2021;

import java.util.List;

public class ConferenceUser {
    private String username;
    private String password;
    private List<String> submissions;
    private boolean speaker;
    private boolean admin;

    public ConferenceUser() {
    }

    public ConferenceUser(ConferenceUser user) {
        this.username = user.username;
        this.password = user.password;
        this.submissions = user.submissions;
        this.speaker = user.speaker;
        this.admin = user.admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<String> submissions) {
        this.submissions = submissions;
    }

    public boolean isSpeaker() {
        return speaker;
    }

    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

