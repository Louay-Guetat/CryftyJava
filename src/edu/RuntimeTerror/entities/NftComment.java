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
public class NftComment {
    int id;
    String content;
    Date postDate;
    int likes;
    int dislikes;
    Nft nft;
    //User user;
    
    public NftComment(){
        
    }
}
