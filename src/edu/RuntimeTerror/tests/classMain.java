/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.RuntimeTerror.tests;

import edu.RuntimeTerror.entities.Personne;
import edu.RuntimeTerror.services.TestCrud;
import edu.RuntimeTerror.utils.DataSource;

/**
 *
 * @author LOUAY
 */
public class classMain {
    public static void main(String[] args) {
        //DataSource dts = new DataSource();
        Personne p = new Personne(13,"Louay","Guetat");
        TestCrud crud = new TestCrud();
        //crud.insertPerson(p);
        //crud.ajouterPersonne(p);
        System.out.println(crud.listerPersonne());
        
        DataSource d1 = DataSource.getInstance();
        DataSource d2 = DataSource.getInstance();
        System.out.println(d1.hashCode() +" /// "+ d2.hashCode());  // meme hashcode donc meme instance :) 
    }
}
