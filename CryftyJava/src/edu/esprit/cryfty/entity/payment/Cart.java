package edu.esprit.cryfty.entity.payment;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Nft;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Cart {
    private int id;
    private double total;
    private Client clientId;
    private Date date_creation;
    private ArrayList<Nft> nftProd;

    public Cart() {
    }

    public Cart(double total, Date date_creation) {
        this.total = total;
        this.date_creation = date_creation;
    }

    public Cart(int id, double total, Client clientId, Date date_creation, ArrayList<Nft> nftProd) {
        this.id = id;
        this.total = total;
        this.clientId = clientId;
        this.date_creation = date_creation;
        this.nftProd = nftProd;
    }

    public Cart(double total, Client clientId, Date date_creation,  ArrayList<Nft>  nftProd) {
        this.total = total;
        this.clientId = clientId;
        this.date_creation = date_creation;
        this.nftProd = nftProd;
    }

    public Cart(double total, Client clientId, Date date_creation) {
        this.total = total;
        this.clientId = clientId;
        this.date_creation = date_creation;
    }

    public Cart(double total, String format) {
    }

    public Cart(ArrayList<Nft> nftProd) {
        this.nftProd = nftProd;
    }

    public Cart( ArrayList<Nft> nftProd,int id) {
        this.id = id;
        this.nftProd = nftProd;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public ArrayList<Nft> getNftProd() {
        return nftProd;
    }

    public void setNftProd(ArrayList<Nft> nftProd) {
        this.nftProd = nftProd;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", total=" + total +
               ", clientId=" + clientId +
                ", date_creation=" + date_creation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return getId() == cart.getId() && Double.compare(cart.getTotal(), getTotal()) == 0 && Objects.equals(getClientId(), cart.getClientId()) && Objects.equals(getDate_creation(), cart.getDate_creation() ) && Objects.equals(getNftProd(), cart.getNftProd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTotal(), getClientId(), getDate_creation()
                , getNftProd());
    }
}
