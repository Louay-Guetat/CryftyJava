//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity;

public class User {
    public int id;
    private String username;
    private String roles;
    private String password;

    public User() {
    }

    public User(int id, String username, String roles, String password) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{id=" + this.id + ", username='" + this.username + '\'' + ", roles='" + this.roles + '\'' + ", password='" + this.password + '\'' + '}';
    }
}
