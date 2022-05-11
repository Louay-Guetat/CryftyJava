package edu.esprit.cryfty.service.user;

import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.utils.BCrypt;
import edu.esprit.cryfty.utils.DataSource;
import edu.esprit.cryfty.utils.Mailapi;
import edu.esprit.cryfty.utils.smsapi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ClientService {
    public ClientService() {
    }

    public boolean verifPassword(String username, String password) {
        String request = "select e.* from user e where username='" + username +"'";
        System.out.println("test");
        try {
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            System.out.println("test2");

            while(rs.next()){
                String passBase=rs.getString("password");
                if(BCrypt.checkpw(password, passBase)){
                    System.out.println("fi222");
                    return true;
                }else
                    return false;

            }

        } catch (SQLException sq) {
            return false;
        }
        System.out.println("fin");
        return false;

    }

    public void addClient(Client client){
        UserService usr = new UserService();
        usr.addUser(client.getUsername(),client.getPassword());
        User user = usr.showUser();
        System.out.println(user);
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
            Date d=new Date();
            CartService cartService = new CartService();
            pst.executeUpdate();

            ClientService clientService = new ClientService();
            Client clientID = clientService.getClientById(user.getId());
            Cart cartAjout=new Cart(0,clientID,d);
            cartService.addCart(cartAjout);
            System.out.println("done");

            //smsapi.sendSMS("+21624032953", "Welcome to cryfty");
            Mailapi.send("eesprit248@gmail.com", "Chedi123456789",client.getEmail(),"New Account " ,"Wellcome to our Site Cryfty");

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
                client.setFirstName(rs.getString(""));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return client;
    }

    public Client getClientByIdTransaction(int id) {
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
}
