package edu.esprit.cryfty.service.user;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.utils.DataSource;
import edu.esprit.cryfty.entity.User.Reclamation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    public void supprimerReclamationFromClient(int id) {
        try {

            String request = "DELETE FROM support_ticket WHERE id=? = '" + id + "'";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.executeUpdate(request);
            System.out.println("Reclamation supprimé.");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addReclamation(Reclamation R){
        String request = "INSERT INTO support_ticket(email,subject,message,name,client_id) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1, R.getEmail());
            pst.setString(2, R.getSubject());
            pst.setString(3, R.getMessage());
            pst.setString(4, R.getName());
            pst.setInt(5, R.getIduser());

            pst.executeUpdate();
            System.out.println("Reclamation added successfully :)\n");



        }catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
    public void updateReclamaton(Reclamation R){
        String request = "update support_ticket set email=?, subject=?, message=?, name=?" +
                "where support_ticket.id = "+R.getId();
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1, R.getEmail());
            pst.setString(2, R.getSubject());
            pst.setString(3, R.getMessage());
            pst.setString(4, R.getName());

            pst.executeUpdate();
            System.out.println("Reclamation updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }



    public List<Reclamation> showReclamations(){
        List<Reclamation> reclamations = new ArrayList();
        String request = "select * from support_ticket";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Reclamation rec = new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setEmail(rs.getString("email"));
                rec.setName(rs.getString("name"));
                rec.setMessage(rs.getString("message"));
                rec.setSubject(rs.getString("subject"));

                reclamations.add(rec);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }


}
