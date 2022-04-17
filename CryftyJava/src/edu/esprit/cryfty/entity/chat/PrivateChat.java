package edu.esprit.cryfty.entity.chat;

import edu.esprit.cryfty.entity.User;

public class PrivateChat extends Conversation{
    private User Sender;
    private User Received;

    public PrivateChat() {
    }

    public PrivateChat(String nom) {
        super(nom);
    }

    public PrivateChat( User sender, User received) {

        Sender = sender;
        Received = received;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public User getReceived() {
        return Received;
    }

    public void setReceived(User received) {
        Received = received;
    }

    @Override
    public String toString() {
        return "PrivateChat{" +
                "id=" + id +
                ", Sender=" + Sender +
                ", Received=" + Received +
                '}';
    }
}
