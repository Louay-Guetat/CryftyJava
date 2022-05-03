package edu.esprit.cryfty.entity.Nft;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Nft {
    int id;
    String image;
    String title;
    String description;
    float price;
    Node currency;
    Category category;
    SubCategory subCategory;
    Client owner;
    LocalDateTime creationDate;
    int likes;

    public Nft(){

    }

    public Nft(int id, String image, String title, String description, float price, Node currency,
               Category category, SubCategory subCategory, Client owner, LocalDateTime creationDate, int likes) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.category = category;
        this.subCategory = subCategory;
        this.owner = owner;
        this.creationDate = creationDate;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Node getCurrency() {
        return currency;
    }

    public void setCurrency(Node currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Nft{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                //", currency=" + currency.getCoinCode() +
                ", category=" + category.getName() +
                ", subCategory=" + subCategory.getName() +
                ", owner=" + owner.getId() +
                ", creationDate=" + creationDate +
                ", likes=" + likes +
                '}';
    }
}
