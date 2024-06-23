package com.example.urlwatcher;

public class Options {
    private String token;
    private String chat_id;
    private int retry;

    Options(String token, String chat_id, int retry){
        this.token = token;
        this.chat_id = chat_id;
        this.retry = retry;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}
