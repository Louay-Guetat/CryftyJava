package edu.esprit.cryfty.service.payment;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.entity.payment.Transaction;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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
            System.out.println("Transaction Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
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

    /*public Wallet getwalletIdClient(int id) {
        ArrayList<Wallet> wallets = new ArrayList();
        String request = "select id,wallet_address from wallet where client_id="+id;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallets.add(wallet);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return wallets.get(0);
    }*/

    public ArrayList<Wallet> getwalletIdClient(int id){
        ArrayList<Wallet> walletEntities = new ArrayList();
        String request = "select id,wallet_address from wallet where client_id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt(1));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                walletEntities.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return walletEntities;
    }

}
