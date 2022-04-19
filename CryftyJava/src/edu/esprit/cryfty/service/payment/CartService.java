package edu.esprit.cryfty.service.payment;

import edu.esprit.cryfty.entity.*;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.net.ssl.ExtendedSSLSession;
import java.io.Console;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
            refreshTable();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerCartfromNftCart(int id) {
        try {

            String request = "delete from nft_cart where cart_id = '" + id + "'";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.executeUpdate(request);
            System.out.println("cart deleted.");
            refreshTable();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }




    public ObservableList<Nft> getNftfromCart(){
        ObservableList<Nft> NftcartEntities=FXCollections.observableArrayList();
        NftService nftService=new NftService();
        List<Nft> nfts = nftService.showNfts();
        String request = "SELECT nft_id FROM nft_cart";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Nft nft=new Nft();
                for (int i =0; i<nfts.size();i++){
                    if(nfts.get(i).getId()==rs.getInt(1))
                        nft = nfts.get(i);
                }
                NftcartEntities.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return NftcartEntities;
    }

    public ObservableList<Cart> getCartfromNft(){
        ObservableList<Cart> NftcartEntities=FXCollections.observableArrayList();
        List<Cart> carts = getCarts();
        String request = "SELECT cart_id FROM nft_cart ";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Cart cart=new Cart();
                for (int i =0; i<carts.size();i++){
                    if(carts.get(i).getId()==rs.getInt(1))
                        cart = carts.get(i);
                }
                NftcartEntities.add(cart);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return NftcartEntities;
    }

    public ArrayList<Nft> getPricefromNftCart(){
        ArrayList<Nft> NftcartEntities=new ArrayList<>();
        NftService nftService=new NftService();
        List<Nft> nfts = nftService.showNfts();
        String request = "SELECT nft_id FROM nft_cart";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Nft nft=new Nft();
                for (int i =0; i<nfts.size();i++){
                    if(nfts.get(i).getId()==rs.getInt(1))
                        nft = nfts.get(i);
                }
                NftcartEntities.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return NftcartEntities;
    }
    private void refreshTable() {
        try {
            ObservableList<Nft> NftcartEntities = FXCollections.observableArrayList();
            NftcartEntities.clear();
            getNftfromCart();
            System.out.println(getNftfromCart());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierCartTotal(float total,int id){
        try {
            String request = " UPDATE Cart SET total=? "
                    +"WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setFloat(1, total);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("total modifié avec succés !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
