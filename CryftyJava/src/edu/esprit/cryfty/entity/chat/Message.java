package edu.esprit.cryfty.entity.chat;

import edu.esprit.cryfty.entity.User.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
    private int id ;
    private String contenu ;
    private String createdAt;
    private Conversation conversation;
    private User Sender;

    public Message(){}
    public Message(String contenu,  Conversation conversation, User sender) {
        this.contenu = contenu;
        this.createdAt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());;
        this.conversation = conversation;
        Sender = sender;
    }

    public Message(String contenu, User sender) {
        this.createdAt =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());;
        this.contenu = contenu;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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
