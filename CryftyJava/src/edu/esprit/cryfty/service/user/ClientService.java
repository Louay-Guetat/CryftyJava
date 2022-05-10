package edu.esprit.cryfty.service.user;

import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.utils.BCrypt;
import edu.esprit.cryfty.utils.DataSource;
import edu.esprit.cryfty.utils.Mailapi;
import edu.esprit.cryfty.utils.smsapi;

import java.sql.*;

public class ClientService {
    public ClientService() {
    }

    public Client getClientById(int id) {
        return new Client();
    }

    public void addClient(Client client){
        UserService usr = new UserService();
        usr.addUser(client.getUsername(),client.getPassword());
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
            smsapi.sendSMS("+21624032953", "Welcome to cryfty");
            Mailapi.send("eesprit248@gmail.com", "Chedi123456789",client.getEmail(),"New Account " ,"Wellcome to our Site Cryfty");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
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


}
