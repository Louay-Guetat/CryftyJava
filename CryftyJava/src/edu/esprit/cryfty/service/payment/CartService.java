package edu.esprit.cryfty.service.payment;

import edu.esprit.cryfty.entity.*;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.utils.DataSource;

import javax.net.ssl.ExtendedSSLSession;
import java.io.Console;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class CartService {
    public static ArrayList<Nft> nfts=new ArrayList<>();
    public static Cart cart=new Cart(nfts);
    public CartService() {
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

    public Cart getCartById(int id) {
        ArrayList<Cart> cartEntities = new ArrayList();
        String request = "SELECT * FROM cart where id ="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Cart cart= new Cart();
                cart.setId(rs.getInt(1));
                cart.setDate_creation(rs.getDate(3));
                cart.setClientId(getClientById(rs.getInt("client_id_id")));
                cart.setTotal(rs.getDouble(4));
                cartEntities.add(cart);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return cartEntities.get(0);
    }

    public ArrayList<Cart> getCarts(){
        ArrayList<Cart> cartEntities = new ArrayList();
        String request = "SELECT * FROM cart";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Cart cart= new Cart();
                cart.setId(rs.getInt(1));
                cart.setDate_creation(rs.getDate(3));
                cart.setClientId(getClientById(rs.getInt("client_id_id")));
                cart.setTotal(rs.getDouble(4));
                cartEntities.add(cart);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return cartEntities;
    }

    public void addCart(Cart cart) {
        try {
            String request = "insert into cart(total,client_id_id,date_creation) VALUES " + "(?,?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setDouble(1, cart.getTotal());
            pst.setInt(2,cart.getClientId().getId());
            pst.setObject(3, cart.getDate_creation());
            pst.executeUpdate();
            System.out.println("Cart Added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addNftToCart(Cart cart1)
    {
        try {
            String request = "insert into nft_cart(nft_id,cart_id) VALUES " + "(?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,cart1.getNftProd().get(0).getId());
            pst.setInt(2,cart1.getId());
            pst.executeUpdate();
            System.out.println(" NFT added to Cart.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerNftFromCart(int id) {
        try {

            String request = "delete from nft_cart where nft_id = '" + id + "'";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.executeUpdate(request);
            System.out.println("nft supprimé du cart.");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
