package edu.esprit.cryfty.entity.Nft;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Category {
    int id;
    String name;
    LocalDateTime creationDate;
    int nbrNfts;
    int nbrSubCategories;
    ArrayList<Nft> nfts = new ArrayList();
    ArrayList<SubCategory> subCategories = new ArrayList();

    public Category(){

    }

    public Category(int id, String name, LocalDateTime creationDate, int nbrNfts, int nbrSubCategories) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.nbrNfts = nbrNfts;
        this.nbrSubCategories = nbrSubCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getNbrNfts() {
        return nbrNfts;
    }

    public void setNbrNfts(int nbrNfts) {
        this.nbrNfts = nbrNfts;
    }

    public int getNbrSubCategories() {
        return nbrSubCategories;
    }

    public void setNbrSubCategories(int nbrSubCategories) {
        this.nbrSubCategories = nbrSubCategories;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", nbrNfts=" + nbrNfts + ", nbrSubCategories=" + nbrSubCategories + '}';
    }
}
