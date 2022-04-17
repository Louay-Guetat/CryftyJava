package edu.esprit.cryfty.entity.chat;

import edu.esprit.cryfty.entity.User;

import java.util.ArrayList;

public class GroupeChat extends Conversation{
private User owner;
private ArrayList<User> Participants= new ArrayList<>();
    public GroupeChat(String name ) {
        super(name);
    }

    public GroupeChat(User owner) {
        this.owner = owner;
    }

    public GroupeChat() {
    }

    public GroupeChat(User owner, ArrayList<User> participants) {
        this.owner = owner;
        Participants = participants;
    }

    public GroupeChat(String nom, User owner, ArrayList<User> participants) {
        super(nom);
        this.owner = owner;
        Participants = participants;
    }

    public ArrayList<User> getParticipants() {
        return Participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        Participants = participants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public GroupeChat(String name ,User owner) {
        super(name);
        this.owner = owner;
    }



    @Override
    public String toString() {
        return "GroupeChat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", owner=" + owner.toString() +"Participants"+Participants.toString()+
                '}';
    }
}
