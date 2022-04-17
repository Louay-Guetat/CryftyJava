package edu.esprit.cryfty.entity.chat;

public class Conversation {
    protected int id ;
    protected String nom ;
    private String type;

    public Conversation(String nom) {
        this.nom = nom;
    }

    public Conversation(String nom, String type) {
        this.type=type;
        this.nom = nom;
    }

    public Conversation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +"type= "+type+
                '}';
    }
}
