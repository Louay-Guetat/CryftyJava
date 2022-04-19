package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientService {
    public ClientService() {
    }

    public void addClient(Client client){
        UserService usr = new UserService();
        usr.addUser(client.getUsername(),client.getRoles(),client.getPassword());
        User user = usr.showUser();
        String request = "insert into client(id,first_name,last_name,email,phone_number,age,address,avatar,couverture) " +
                "values(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,user.getId());
            pst.setString(2,client.getFirstName());
            pst.setString(3,client.getLastName());
            pst.setString(4,client.getEmail());
            pst.setInt(5,client.getPhoneNumber());
            pst.setInt(6,client.getAge());
            pst.setString(7,client.getAddress());
            pst.setString(8,client.getAvatar());
            pst.setString(9,client.getCouverture());

            pst.executeUpdate();
            System.out.println("done");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Client getClientById(int id) {
        UserService userService = new UserService();
        Client client = new Client();
        String request = "select * from client where id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                client.setId(rs.getInt("id"));
                client.setUsername(userService.findUserById(client.getId()).getUsername());
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return client;
    }

}
