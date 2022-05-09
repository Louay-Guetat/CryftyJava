package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WalletService {
    ClientService clientService;
    NodeService nodeService;
    public WalletService() {
        this.clientService = new ClientService();
        this.nodeService = new NodeService();
    }


    public ArrayList<Wallet> getWallets(){
        String request = "select * from wallet";
        ArrayList<Wallet> wallets = new ArrayList<>();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setActive(rs.getBoolean("is_active"));
                wallet.setMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
                wallets.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallets;
    }

    public void addWallet(Wallet wallet){
        String request = "insert into wallet(wallet_label,wallet_image_file_name,client_id,is_main,is_active,balance," +
                "node_id_id,wallet_address) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, wallet.getWalletLabel());
            st.setString(2, wallet.getWalletImageFileName());
            st.setInt(3, wallet.getClient().getId());
            st.setBoolean(4, wallet.isMain());
            st.setBoolean(5, wallet.isActive());
            st.setDouble(6, wallet.getBalance());
            st.setInt(7, wallet.getNode().getId());
            st.setString(8, "1234526");
            st.executeUpdate();
            System.out.println("Wallet Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateWallet(Wallet wallet){
        String request = "update wallet set wallet_label=? , wallet_image_file_name=? where id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, wallet.getWalletLabel());
            st.setString(2, wallet.getWalletImageFileName());
            st.setInt(3, wallet.getId());
            st.executeUpdate();
            System.out.println("Wallet Updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Wallet getWalletById(int id){
        String request = "select * from wallet where id="+id;
        Wallet wallet = new Wallet();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setActive(rs.getBoolean("is_active"));
                wallet.setMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallet;
    }

    public Wallet getWalletByAddress(String address){
        String request = "select * from wallet where wallet_address="+address;
        Wallet wallet = new Wallet();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setActive(rs.getBoolean("is_active"));
                wallet.setMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallet;
    }

    public ArrayList<Wallet> getWalletsByClient(int clientId){
        String request = "select * from wallet where client_id="+clientId;
        ArrayList<Wallet> wallets = new ArrayList<>();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setActive(rs.getBoolean("is_active"));
                wallet.setMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
                wallets.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallets;
    }

    public void deleteWallet(Wallet wallet){
        String request = "delete from wallet where id="+wallet.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Wallet deleted Successfully");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void deleteWallet(int id){
        String request = "delete from wallet where id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Wallet deleted Successfully");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateBalanceWallet(Wallet wallet, double balance) {
        String request = "update wallet set balance="+balance+" where wallet.id = " + wallet.getId();
        try {
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.executeUpdate();
            System.out.println("balance updated");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

}
