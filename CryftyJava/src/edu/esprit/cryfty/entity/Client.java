//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity;

import java.util.Objects;

public class Client extends User {
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private int age;
    private String address;
    private String avatar;
    private String couverture;

    public Client() {
    }

    public Client(int id,String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id=id;
    }

    public Client(int id, String username, String roles, String password, String firstName, String lastName, String email, int phoneNumber, int age, String address, String avatar, String couverture) {
        super(id, username, roles, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
        this.avatar = avatar;
        this.couverture = couverture;
    }

    public Client(int id, String username) {
        this.id = id;
        this.setUsername(username);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCouverture() {
        return this.couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Client client = (Client)o;
            return this.id == client.id && this.age == client.age && Objects.equals(this.firstName, client.firstName) && Objects.equals(this.lastName, client.lastName) && Objects.equals(this.email, client.email) && Objects.equals(this.phoneNumber, client.phoneNumber) && Objects.equals(this.address, client.address) && Objects.equals(this.avatar, client.avatar) && Objects.equals(this.couverture, client.couverture);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.firstName, this.lastName, this.email, this.phoneNumber, this.age, this.address, this.avatar, this.couverture});
    }

    public String toString() {
        return "Client{id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", email='" + this.email + '\'' + ", phoneNumber='" + this.phoneNumber + '\'' + ", age=" + this.age + ", address='" + this.address + '\'' + ", avatar='" + this.avatar + '\'' + ", couverture='" + this.couverture + '\'' + '}';
    }

}
