package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.utils.DataSource;

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
        ArrayList<Wallet> walletEntities = new ArrayList();
        String request = "select * from wallet";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("wallet_balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setActive(rs.getBoolean("is_active"));
                wallet.setMain(rs.getBoolean("is_main"));
                walletEntities.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return walletEntities;
    }

    public void addWallet(Wallet wallet){
        String request = "insert into wallet(wallet_label,wallet_image_file_name,client_id,is_main,is_active,balance,node_id_id,wallet_address) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            st.executeUpdate(request);
            System.out.println("Wallet Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateWallet(){

    }

    public Wallet getWalletById(){

    }

    public Wallet getWalletByAddress(){

    }

    public ArrayList<Wallet> getWalletsByClient(){

    }

    public void deleteWallet(){

    }


}
