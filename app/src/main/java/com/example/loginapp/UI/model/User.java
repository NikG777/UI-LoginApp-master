package com.example.loginapp.UI.model;

public class User {
    private int id;
    private String email;
    private String token;
    private String url;
    private  String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
