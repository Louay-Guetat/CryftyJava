package edu.esprit.cryfty.service.payment;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.cryfty.entity.Block;
import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.entity.payment.Transaction;
import edu.esprit.cryfty.service.ClientService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.service.WalletService;
import edu.esprit.cryfty.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import com.itextpdf.text.Document;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;


public class TransactionService {


    public TransactionService() {
    }

    public Cart getCartById(int id) {
        ArrayList<Cart> cart = new ArrayList();
        String request = "select id,date_creation,total  from cart where id ="+id ;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Cart c = new Cart();
                c.setId(rs.getInt(1));
                c.setDate_creation(rs.getDate(2));
                c.setTotal(rs.getDouble(3));
                cart.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cart.get(0);
    }
    public ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> transactionEntities = new ArrayList();
        String request = "SELECT * FROM transaction";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Transaction transaction= new Transaction();
                transaction.setId(rs.getInt(1));
                transaction.setDatetransaction(rs.getDate(4));
                //System.out.println(getCartById(rs.getInt("cart_id_id")));
                //transaction.setCartId(getCartById(rs.getInt("cart_id_id")));
                transaction.setCartId(getCartById(rs.getInt("cart_id_id")));
                transactionEntities.add(transaction);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return transactionEntities;
    }



    public void addTransaction(Transaction transaction){
        try{
            String request = "insert into transaction(cart_id_id,wallets_id,datetransaction) " +
                    "VALUES (?,?,?)";
            PreparedStatement pst=DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,transaction.getCartId().getId());
            pst.setInt(2,transaction.getWallets().getId());
            pst.setObject(3,transaction.getDatetransaction());
            pst.executeUpdate();
            balanceTransaction();
            System.out.println("Transaction Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Transaction> getLastTransaction()
    {
        ArrayList<Transaction> transactionEntities = new ArrayList();
        String request = "select  max(id),cart_id_id,wallets_id from transaction order by id desc ";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){

                Transaction tr=new Transaction();
                tr.setId(rs.getInt(1));
                tr.setCartId(getCartById(rs.getInt("cart_id_id")));
                tr.setWallets(getwalletId(rs.getInt("wallets_id")));
                transactionEntities.add(tr);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return transactionEntities;
    }

    public ArrayList<Wallet> walletFindOneByClient(int id)
    {
        ArrayList<Wallet> walletEntities = new ArrayList();
        NodeService nodeService=new NodeService();
        String request = "SELECT id,wallet_label,wallet_address,balance,is_main,node_id_id FROM wallet where client_id="+id+" and is_main=true";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet=new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setIsMain(rs.getBoolean("is_main"));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                walletEntities.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return walletEntities;
    }

    public ArrayList<Block> blockFindByWallet(int id)
    {
        ArrayList<Block> blockEntities = new ArrayList();
        String request = "SELECT id,hash FROM block where wallet_id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Block block=new Block();
                block.setId(rs.getInt(1));
                block.setHash(rs.getString(2));
                blockEntities.add(block);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return blockEntities;
    }

    public void updateBlockAuth(Block block,int id) {
        String request = "update block set wallet_id="+id+" where block.id = " + block.getId();
        try {
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.executeUpdate();
            System.out.println("authBlock updated");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }
    public void balanceTransaction(){

        CartService cartService=new CartService();
        WalletService walletService=new WalletService();
        CartService c=new CartService();
        Client c1=c.getClientById(1);

        //jebna cart
        Cart idCart=getLastTransaction().get(0).getCartId();


        //jebna nft fel cart array
        ObservableList<Nft> cartNft= cartService.getNftfromCart();


        NftService nftService=new NftService();
           for(Nft nft: cartNft)
            {
                Client author=nft.getOwner();
                nftService.updateOwnerNft(nft,c1.getId());

                //client qui va payer
                Wallet buyerWallet=walletFindOneByClient(c1.getId()).get(0);

                //wallet tezedelha flous
                Wallet authWallet=walletFindOneByClient(author.getId()).get(0);


                double balanceAuth=authWallet.getBalance()+nft.getPrice();
                double balanceBuyer=buyerWallet.getBalance()-nft.getPrice();


                walletService.updateBalanceWallet(authWallet,balanceAuth);
                walletService.updateBalanceWallet(buyerWallet,balanceBuyer);


            }
    }

    public Wallet getwalletId(int id) {
        ArrayList<Wallet> wallets = new ArrayList();
        String request = "select id,wallet_label,wallet_address from wallet where id="+id;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallets.add(wallet);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return wallets.get(0);
    }

    public ObservableList<Transaction> getTransactionsByClient(int idClient){
        ObservableList<Transaction> transactionEntities= FXCollections.observableArrayList();
        String request = "SELECT * FROM  transaction t join wallet w on t.wallets_id=w.id where w.client_id="+idClient;
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Transaction transaction= new Transaction();

                transaction.setId(rs.getInt(1));
                transaction.setCartId(getCart2ById(rs.getInt("cart_id_id")));
                transaction.setWallets(getwalletId(rs.getInt("wallets_id")));
                transaction.setDatetransaction(rs.getDate(4));
                transactionEntities.add(transaction);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return transactionEntities;
    }

    public ArrayList<Wallet> getwalletIdClient(int id){
        ArrayList<Wallet> walletEntities = new ArrayList();
        String request = "select id,wallet_address,balance from wallet where client_id="+id+" and is_main=true";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance((rs.getDouble("balance")));
                walletEntities.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return walletEntities;
    }
    public Wallet getwalletsAddress(String walletAddress) {
        ArrayList<Wallet> wallets = new ArrayList<>();
        String request = "select id,wallet_label,wallet_address from wallet where wallet_address= ?";
        try {
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1,walletAddress);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallets.add(wallet);
                JOptionPane.showMessageDialog(null,"Transaction added with success");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return wallets.get(0);
    }



    public Cart getCart2ById(int id) {
        ArrayList<Cart> cart = new ArrayList();
        ClientService clientService=new ClientService();
        String request = "select id,date_creation,total,client_id_id  from cart where id ="+id ;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Cart c = new Cart();
                c.setId(rs.getInt(1));
                c.setDate_creation(rs.getDate(2));
                c.setTotal(rs.getDouble(3));
                c.setClientId(clientService.getClientById(rs.getInt(4)));
                cart.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cart.get(0);
    }

}
