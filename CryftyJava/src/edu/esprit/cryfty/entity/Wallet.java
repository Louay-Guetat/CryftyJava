package edu.esprit.cryfty.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Wallet {
    private int id;
    private String walletAddress;
    private String walletLabel;
    private String walletImageFileName;
    private double balance;
    private boolean isMain;
    private boolean isActive;
    private Client client;
    private Node node;
    private ArrayList<Block> blocks;


    public Wallet() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getWalletLabel() {
        return walletLabel;
    }

    public void setWalletLabel(String walletLabel) {
        this.walletLabel = walletLabel;
    }

    public String getWalletImageFileName() {
        return walletImageFileName;
    }

    public void setWalletImageFileName(String walletImageFileName) {
        this.walletImageFileName = walletImageFileName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return id == wallet.id && Double.compare(wallet.balance, balance) == 0 && isMain == wallet.isMain && isActive == wallet.isActive && Objects.equals(walletAddress, wallet.walletAddress) && Objects.equals(walletLabel, wallet.walletLabel) && Objects.equals(walletImageFileName, wallet.walletImageFileName) && Objects.equals(client, wallet.client) && Objects.equals(node, wallet.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletAddress, walletLabel, walletImageFileName, balance, isMain, isActive, client, node);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", walletAddress='" + walletAddress + '\'' +
                ", walletLabel='" + walletLabel + '\'' +
                ", walletImageFileName='" + walletImageFileName + '\'' +
                ", balance=" + balance +
                ", isMain=" + isMain +
                ", isActive=" + isActive +
                ", client=" + client +
                ", node=" + node +
                '}';
    }
}
