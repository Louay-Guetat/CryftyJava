package edu.esprit.cryfty.entity.chat;

import edu.esprit.cryfty.entity.User.User;

import java.util.Date;

public class Message {
    private int id ;
    private String contenu ;
    private Date createdAt;
    private Conversation conversation;
    private User Sender;
    public Message(){}
    public Message(String contenu,  Conversation conversation, User sender) {
        this.contenu = contenu;
        this.createdAt =new Date();
        this.conversation = conversation;
        Sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", createdAt=" + createdAt +
                ", conversation=" + conversation +
                ", Sender=" + Sender +
                '}';
    }
}
