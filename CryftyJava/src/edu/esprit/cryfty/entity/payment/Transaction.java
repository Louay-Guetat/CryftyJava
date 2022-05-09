package edu.esprit.cryfty.entity.payment;

import edu.esprit.cryfty.entity.Wallet;

import java.util.Date;
import java.util.Objects;

public class Transaction {
    private int id;
    private Cart cartId;
    private Date datetransaction;
    private Wallet wallets;

    public Transaction() {
    }

    public Transaction(int id, Cart cartId, Date datetransaction, Wallet wallets) {
        this.id = id;
        this.cartId = cartId;
        this.datetransaction = datetransaction;
        this.wallets = wallets;
    }

    public Transaction(int id, Cart cartId, Date datetransaction) {
        this.id = id;
        this.cartId = cartId;
        this.datetransaction = datetransaction;
    }

    public Transaction(Cart cartId, Wallet wallets, Date datetransaction) {
        this.cartId = cartId;
        this.datetransaction = datetransaction;
        this.wallets = wallets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCartId() {
        return cartId;
    }

    public void setCartId(Cart cartId) {
        this.cartId = cartId;
    }

    public Date getDatetransaction() {
        return datetransaction;
    }

    public void setDatetransaction(Date datetransaction) {
        this.datetransaction = datetransaction;
    }

    public Wallet getWallets() {
        return wallets;
    }

    public void setWallets(Wallet wallets) {
        this.wallets = wallets;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", datetransaction=" + datetransaction +
                ", wallets=" + wallets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getId() == that.getId() && Objects.equals(getCartId(), that.getCartId()) && Objects.equals(getDatetransaction(), that.getDatetransaction()) && Objects.equals(getWallets(), that.getWallets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCartId(), getDatetransaction(), getWallets());
    }


}
