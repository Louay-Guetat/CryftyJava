package edu.esprit.cryfty.entity.User;

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

    public Client(int id,String username, String roles,String password,String firstName, String lastName,
                  String email, int phoneNumber, int age, String address,String avatar,String couverture) {
        super(id,username,roles,password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
        this.avatar = avatar;
        this.couverture = couverture;
    }

    public Client(int i, String s) {
        super();

    }

    public Client(int id, String username, String password, String firstname, String lastname, String email, int phonenumber, int age, String address, String h, String h1) {
        super(id,username,"[\"ROLE_CLIENT\"]",password);
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.phoneNumber = phonenumber;
        this.age = age;
        this.address = address;
        this.avatar = h;
        this.couverture = h1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && age == client.age && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(email, client.email) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(address, client.address) && Objects.equals(avatar, client.avatar) && Objects.equals(couverture, client.couverture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, age, address, avatar, couverture);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", couverture='" + couverture + '\'' +
                '}';
    }

}
