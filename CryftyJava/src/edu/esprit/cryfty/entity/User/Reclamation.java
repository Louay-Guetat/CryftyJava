package edu.esprit.cryfty.entity.User;


public class Reclamation {

    private int id;
    private String email,subject,message,name;
    private int iduser;

    public Reclamation() {
    }

    public Reclamation(int id, String email, String subject, String message, String name,int iduser ) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.name = name;
        this.iduser = iduser;
    }

    public Reclamation(int id, String email, String subject, String message, String name) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.name = name;
    }

    public Reclamation(String email, String subject, String message, String name) {
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.name = name;
    }
    public Reclamation(String email, String subject, String message, String name,int iduser) {
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.name = name;
        this.iduser = iduser;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}