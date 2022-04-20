//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity.Nft;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Node;
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
    Date creationDate;
    int likes;

    public Nft() {
    }

    public Nft(int id, String image, String title, String description, float price, Node currency, Category category, SubCategory subCategory, Client owner, Date creationDate, int likes) {
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

    public Nft(String title, String description, float price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Nft(int id, String title, String description, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Node getCurrency() {
        return this.currency;
    }

    public void setCurrency(Node currency) {
        this.currency = currency;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Client getOwner() {
        return this.owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String toString() {
        return "Nft{image='" + this.image + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", price=" + this.price + ", category=" + this.category.getName() + ", subCategory=" + this.subCategory.getName() + ", owner=" + this.owner.getId() + ", creationDate=" + this.creationDate + ", likes=" + this.likes + '}';
    }
}

