//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity.Nft;

import java.util.ArrayList;
import java.util.Date;

public class Category {
    int id;
    String name;
    Date creationDate;
    int nbrNfts;
    int nbrSubCategories;
    ArrayList<Nft> nfts = new ArrayList();
    ArrayList<SubCategory> subCategories = new ArrayList();

    public Category() {
    }

    public Category(int id, String name, Date creationDate, int nbrNfts, int nbrSubCategories) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.nbrNfts = nbrNfts;
        this.nbrSubCategories = nbrSubCategories;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getNbrNfts() {
        return this.nbrNfts;
    }

    public void setNbrNfts(int nbrNfts) {
        this.nbrNfts = nbrNfts;
    }

    public int getNbrSubCategories() {
        return this.nbrSubCategories;
    }

    public void setNbrSubCategories(int nbrSubCategories) {
        this.nbrSubCategories = nbrSubCategories;
    }

    public String toString() {
        return "Category{id=" + this.id + ", name=" + this.name + ", creationDate=" + this.creationDate + ", nbrNfts=" + this.nbrNfts + ", nbrSubCategories=" + this.nbrSubCategories + '}';
    }
}
