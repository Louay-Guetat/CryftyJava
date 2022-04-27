package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ClientService {
    public ClientService() {
    }

    public Client getClientById(int id) {
        ArrayList<Client> clients = new ArrayList();
        String request = "select * from client where id ="+id ;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt(1));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setAddress(rs.getString("address"));
                client.setAge(rs.getInt("age"));
                client.setAvatar(rs.getString("avatar"));
                client.setCouverture(rs.getString("couverture"));
                client.setPhoneNumber(rs.getInt("phone_number"));
                clients.add(client);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clients.get(0);
    }

    public void addClient(Client client){
        UserService usr = new UserService();
        CartService cartService=new CartService();
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
            Date d=new Date();
            client.setId(user.getId());
            Cart cartAjout=new Cart(0,client,d);
            cartService.addCart(cartAjout);
            System.out.println("done");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
