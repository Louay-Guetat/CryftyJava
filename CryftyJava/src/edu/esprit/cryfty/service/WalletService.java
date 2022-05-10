package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Transfer;
import edu.esprit.cryfty.entity.Block;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class WalletService {
    ClientService clientService;
    NodeService nodeService;
    public WalletService() {
        this.clientService = new ClientService();
        this.nodeService = new NodeService();
    }


    public ObservableList<Wallet> getWallets(){
        String request = "select * from wallet";
        ObservableList<Wallet> wallets = FXCollections.observableArrayList();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Wallet wallet = new Wallet();
                wallet.setId(rs.getInt("id"));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setIsActive(rs.getBoolean("is_active"));
                wallet.setIsMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
                wallets.add(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallets;
    }

    public ArrayList<Transfer> getTransfers(){
        Wallet wallet = getClientMainWallet(2);
        String request = "select * from transfer where sender_id_id="+wallet.getId()+" OR reciever_id_id="+wallet.getId();
        ArrayList<Transfer> transfers = new ArrayList<>();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Transfer transfer = new Transfer();
                transfer.setId(rs.getInt(1));
                transfer.setSender(getWalletById(rs.getInt("sender_id_id")).getWalletLabel());
                transfer.setReceiver(getWalletById(rs.getInt("reciever_id_id")).getWalletLabel());
                transfer.setAmount(rs.getString("amount"));
                transfer.setDate(rs.getString("transfer_date"));
            transfers.add(transfer);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return transfers;
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
            st.setBoolean(4, wallet.isIsMain());
            st.setBoolean(5, wallet.isIsActive());
            st.setDouble(6, wallet.getBalance());
            st.setInt(7, wallet.getNode().getId());
            st.setString(8, wallet.getWalletAddress());
            st.executeUpdate();
            System.out.println("Wallet Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public void mineCrypto(Wallet wallet){
        Block block = new Block();
        byte[] b = new byte[18];
        new Random().nextBytes(b);
        byte[] hexChars = new byte[b.length * 2];
        for (int j = 0; j < b.length; j++) {
            int v = b[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        String hash = new String(hexChars, StandardCharsets.UTF_8);
        System.out.println("Block's Hash = "+hash);
        block.setHash(hash);
        block.setNode(wallet.getNode());
        block.setWallet(wallet);
        block.setPreviousHash(getLatestBlock().getHash());
        String request = "insert into block(node_id,wallet_id,hash,previous_hash) VALUES (?,?,?,?)";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, String.valueOf(block.getNode().getId()));
            st.setString(2, String.valueOf(block.getWallet().getId()));
            st.setString(3, block.getHash());
            st.setString(4, block.getPreviousHash());
            st.executeUpdate();
            System.out.println("Block Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        request = "select count(*) from block where wallet_id="+block.getWallet().getId();
        int count =1;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                count = rs.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        wallet.setBalance(wallet.getNode().getNodeReward() * count);
        request = "update wallet set balance=? where id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setDouble(1, wallet.getBalance());
            st.setInt(2, wallet.getId());
            st.executeUpdate();
            System.out.println("Wallet Updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Block getLatestBlock(){
        String request = "select * from block ORDER BY id DESC LIMIT 1";
        Block block = new Block();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                block.setId(rs.getInt(1));
                block.setHash(rs.getString("hash"));
                System.out.println(block);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("Latest Block = "+block.getHash());
        if (block.getHash() == null){
            block.setHash("0");
        }
        return block;
    }
    public void updateWallet(Wallet wallet){
        String request = "update wallet set wallet_label=? , wallet_image_file_name=?, is_main=? where id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, wallet.getWalletLabel());
            st.setString(2, wallet.getWalletImageFileName());
            st.setInt(4, wallet.getId());
            st.setBoolean(3, wallet.isIsMain());
            if(wallet.isIsMain()) {
                String request2 = "update wallet set is_main=false where client_id=?";
                PreparedStatement st2 = DataSource.getInstance().getCnx().prepareStatement(request2);
                st2.setInt(1,1); //Replace with ID from session
                st2.executeUpdate();
            }
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
                wallet.setIsActive(rs.getBoolean("is_active"));
                wallet.setIsMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallet;
    }

    public Wallet getWalletByAddress(String address){
        String request = "select * from wallet where wallet_address="+ address;
        Wallet wallet = new Wallet();
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                wallet.setId(rs.getInt(1));
                wallet.setWalletLabel(rs.getString("wallet_label"));
                wallet.setWalletAddress(rs.getString("wallet_address"));
                wallet.setBalance(rs.getDouble("balance"));
                wallet.setWalletImageFileName(rs.getString("wallet_image_file_name"));
                wallet.setClient(clientService.getClientById(rs.getInt("client_id")));
                wallet.setNode(nodeService.getNodeById(rs.getInt("node_id_id")));
                wallet.setIsActive(rs.getBoolean("is_active"));
                wallet.setIsMain(rs.getBoolean("is_main"));
                System.out.println(wallet);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return wallet;
    }

    public Wallet getClientMainWallet(int idClient){
        String request = "select * from wallet where is_main = true and client_id="+idClient;
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
                wallet.setIsActive(rs.getBoolean("is_active"));
                wallet.setIsMain(rs.getBoolean("is_main"));
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
                wallet.setIsActive(rs.getBoolean("is_active"));
                wallet.setIsMain(rs.getBoolean("is_main"));
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


    public String generateAddress() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 34;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public boolean transferCrypto(Wallet wallet, String sent, double amount) {
        try {

        Wallet receiver = getWalletByAddress(sent);
            System.out.println(receiver);
        String request = "select * from block where wallet_id="+wallet.getId();
        ArrayList<Block> blocks = new ArrayList<>();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Block block = new Block();
                block.setId(rs.getInt(1));
                block.setWallet(wallet);
                block.setHash(rs.getString("hash"));
                block.setPreviousHash(rs.getString("previous_hash"));
                blocks.add(block);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        int counter = (int) ((amount/wallet.getNode().getNodeReward())+1);
        for (Block block : blocks) {
            if (counter >= 0) {
                block.setWallet(receiver);
                updateBlock(block);
            }
            counter--;
        }
        addTransfer(wallet,receiver,amount);
            request = "select count(*) from block where wallet_id="+wallet.getId();
            int count =1;
            try{
                Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
                ResultSet rs = st.executeQuery(request);
                while(rs.next()){
                    count = rs.getInt(1);
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }

            wallet.setBalance(wallet.getNode().getNodeReward() * count);
            request = "update wallet set balance=? where id=?";
            try{
                PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
                st.setDouble(1, wallet.getBalance());
                st.setInt(2, wallet.getId());
                st.executeUpdate();
                System.out.println("Wallet Updated.");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }



            request = "select count(*) from block where wallet_id="+receiver.getId();
            try{
                Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
                ResultSet rs = st.executeQuery(request);
                while(rs.next()){
                    count = rs.getInt(1);
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }

            receiver.setBalance(receiver.getNode().getNodeReward() * count);
            request = "update wallet set balance=? where id=?";
            try{
                PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
                st.setDouble(1, receiver.getBalance());
                st.setInt(2, receiver.getId());
                st.executeUpdate();
                System.out.println("Wallet Updated.");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }


        return true;
        }catch (Exception e){
            return false;
        }
    }

    private void addTransfer(Wallet wallet, Wallet receiver, double amount) {
        String request = "insert into transfer(sender_id_id, reciever_id_id, amount, transfer_date) " +
                "VALUES (?,?,?,?)";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1, wallet.getId());
            st.setInt(2, receiver.getId());
            st.setDouble(3, amount);
            st.setDate(4,new Date(System.currentTimeMillis()));
            st.executeUpdate();
            System.out.println("Transfer Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void updateBlock(Block block) {
        String request = "update block set wallet_id=? where id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1, block.getWallet().getId());
            st.setInt(2, block.getId());
            st.executeUpdate();
            System.out.println("block ownership updated.");
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
