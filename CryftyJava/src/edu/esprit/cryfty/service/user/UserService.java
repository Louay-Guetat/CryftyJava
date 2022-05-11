package edu.esprit.cryfty.service.user;

import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.chat.PrivateChatService;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.utils.BCrypt;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    Connection cnx = DataSource.getInstance().getCnx();
    public void addUser(String username,String password){
        String request = "insert into user(username,roles,password,type,is_active) values(?,?,?,?,0)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            String password2= BCrypt.hashpw(password, BCrypt.gensalt());
            pst.setString(1,username);
            pst.setString(2,"[\"ROLE_CLIENT\"]");
            pst.setString(3,password2);
            pst.setString(4,"client");
            pst.executeUpdate();
            PrivateChatService privateChatService = new PrivateChatService();
            privateChatService.AjouterPrivateChat(showUser().getId());
            System.out.println("Utilisateur ajoute");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public User showUser(){
        User user = new User();
        String request = "SELECT * FROM user ORDER BY ID DESC LIMIT 1";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            rs.next();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return user;
    }

    public void addAdmin(String username,String roles,String password){
        String request = "insert into user(username,roles,password,type,is_active) values(?,?,?,?,0)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,username);
            pst.setString(2,roles);
            pst.setString(3,password);
            pst.setString(4,"Admin");
            pst.executeUpdate();
            System.out.println("Admin ajoute");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public User showAdmin(){
        User user = new User();
        String request = "SELECT * FROM user ORDER BY ID DESC LIMIT 1";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            rs.next();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return user;
    }
    public void addModerator(String username,String roles,String password){
        String request = "insert into user(username,roles,password,type,is_active) values(?,?,?,?,0)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,username);
            pst.setString(2,roles);
            pst.setString(3,password);
            pst.setString(4,"client");
            pst.executeUpdate();
            System.out.println("Utilisateur ajoute");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public User showModerator(){
        User user = new User();
        String request = "SELECT * FROM user ORDER BY ID DESC LIMIT 1";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            rs.next();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return user;
    }

    public User getByidd(int id) {
        User t = new User();
        int idd=0;

        try {

            String req = ("Select * from User WHERE id = '" + id+"'");
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            rs.next();

            t.setId(rs.getInt("id"));
            t.setUsername(rs.getString("username"));
            t.setRoles(rs.getString("roles"));
            idd = t.getId();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    public void updateProfil(Client client){
        String request = "update client set first_name=?, last_name=?, email=?, phone_number=?" +
                " where id = "+ client.getId();
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,client.getFirstName());
            pst.setString(2,client.getLastName());
            pst.setString(3,client.getEmail());
            pst.setInt(4,client.getPhoneNumber());

            pst.executeUpdate();
            System.out.println("Profile updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public int findIdByEmail(String username){
        try {
            String role="";
            Statement stm =cnx.createStatement();
            String querry ="SELECT * FROM `user` where username='"+username+"'";
            ResultSet rs= stm.executeQuery(querry);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User findUserById(int id) {
        User user = new User();
        String request = "select * from user where id=" + id;

        try {
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);

            while(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }

        return user;
    }

}
