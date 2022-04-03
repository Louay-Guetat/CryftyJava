/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.RuntimeTerror.entities;

import java.util.Date;

/**
 *
 * @author LOUAY
 */
public class Nft {
    int id; 
    String image;
    String title;
    String description;
    float price; 
    //Node currency; 
    Category category;
    SubCategory subCategory;
    //Client owner; 
    Date creationDate;
    int likes;
    
    public Nft(){
        
    }   
    
}
