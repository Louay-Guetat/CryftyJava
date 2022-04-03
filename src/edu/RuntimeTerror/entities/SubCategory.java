/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.RuntimeTerror.entities;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author LOUAY
 */
public class SubCategory {
    int id; 
    String name; 
    Date creationDate;
    int nbrNfts;
    Category category;
    ArrayList<Nft> nfts = new ArrayList();
    
    public SubCategory(){
        
    }

    public SubCategory(int id, String name, Date creationDate, int nbrNfts, Category category) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.nbrNfts = nbrNfts;
        this.category = category;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getNbrNfts() {
        return nbrNfts;
    }

    public void setNbrNfts(int nbrNfts) {
        this.nbrNfts = nbrNfts;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SubCategory{" + "id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", nbrNfts=" + nbrNfts + ", category=" + category + '}';
    }
    
}
