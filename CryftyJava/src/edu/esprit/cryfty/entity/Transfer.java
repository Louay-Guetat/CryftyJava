package edu.esprit.cryfty.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Transfer {
    private int id;
    private StringProperty sender;
    private StringProperty receiver;
    private StringProperty amount;
    private StringProperty date;

    public Transfer() {
        this.sender = new SimpleStringProperty("sender");
        this.receiver = new SimpleStringProperty("sender");
        this.amount = new SimpleStringProperty("sender");
        this.date = new SimpleStringProperty("sender");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender.get();
    }

    public StringProperty senderProperty() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender.set(sender);
    }

    public String getReceiver() {
        return receiver.get();
    }

    public StringProperty receiverProperty() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver.set(receiver);
    }

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
