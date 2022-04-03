/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.RuntimeTerror.services;

import edu.RuntimeTerror.entities.Personne;
import edu.RuntimeTerror.utils.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LOUAY
 */
public class TestCrud {
    public void insertPerson(Personne per){
        String request = "insert into personne(nom,prenom) values('"+per.getNom()+"','"+per.getPrenom()+"')";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            st.executeUpdate(request);      //insert delete et update (executeQuery for select)
            System.out.println("Personne ajouté.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
         
    }
    
    //autre methode
    public void ajouterPersonne(Personne per){
        String request = "insert into Personne(nom,prenom) values"+"(?,?)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1, per.getNom());
            pst.setString(2, per.getPrenom());
            pst.executeUpdate();
            System.out.println("Personne inséré.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    public List<Personne> listerPersonne(){
        List<Personne> myList = new ArrayList();
        String request = "select * from personne";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Personne p = new Personne();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                
                myList.add(p);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return myList;
    }
}
