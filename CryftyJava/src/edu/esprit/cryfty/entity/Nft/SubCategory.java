//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity.Nft;

import java.util.ArrayList;
import java.util.Date;

public class SubCategory {
    int id;
    String name;
    Date creationDate;
    int nbrNfts;
    Category category;
    ArrayList<Nft> nfts = new ArrayList();

    public SubCategory() {
    }

    public SubCategory(int id, String name, Date creationDate, int nbrNfts, Category category) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.nbrNfts = nbrNfts;
        this.category = category;
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String toString() {
        return "SubCategory{id=" + this.id + ", name=" + this.name + ", creationDate=" + this.creationDate + ", nbrNfts=" + this.nbrNfts + ", category=" + this.category + '}';
    }
}
