package edu.esprit.cryfty.service.user;

import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.utils.DataSource;

public class Session {
    public static Session session;
    private User currentUser;
    private Session() {
            currentUser = new User();
    }
    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }
        else{
            return session;
        }
        return session;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
