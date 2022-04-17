package edu.esprit.cryfty.service.chat;

import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.PrivateChat;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConversationService {


    public void AjouterConversation(Conversation p)
    {
        if(p instanceof PrivateChat) {
            String req = "INSERT INTO private_chat (nom) VALUES " + "(?)";
            try {
                PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(req);
                pst.setString(1, p.getNom());

                pst.executeUpdate();
                System.out.println("private chat inserer");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
