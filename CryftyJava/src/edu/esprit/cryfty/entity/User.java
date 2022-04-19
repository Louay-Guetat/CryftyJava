package edu.esprit.cryfty.entity;

import javafx.scene.control.CheckBox;

public class User {
    public int id;
    private String username;
    private String roles;
    private String password;
    private CheckBox select;
    public User() {
    }

    public User(int id,String username, String roles, String password){
        this.id = id;
        this.username = username;
        this.roles= roles;
        this.password = password;
    }

    public User(int id ,String username, CheckBox select) {
        this.username = username;
        this.select = select;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}