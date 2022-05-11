package edu.esprit.cryfty.service.chat;

import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.PrivateChat;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PrivateChatService {
    public ArrayList<Conversation> getLastConversation(){
        ArrayList<Conversation> ConversationEntities = new ArrayList();
        String request = "select  max(id) from conversation order by id desc ";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);

            while(rs.next()){

                Conversation gr=new Conversation();
                gr.setId(rs.getInt(1));

                ConversationEntities.add(gr);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return ConversationEntities;
    }

    public void AjouterConversation(Conversation c)

    {    String reqConv="INSERT INTO conversation (nom,type) VALUES "+"(?,?)";

        try {
            PreparedStatement pst= DataSource.getInstance().getCnx().prepareStatement(reqConv);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getType());
            pst.executeUpdate();

            System.out.println("conversation inserer");


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User getUserById(int id) {
        ArrayList<User> user = new ArrayList();
        String request = "select id ,username from user where id ="+id ;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                User gr = new User();
                gr.setId(rs.getInt(1));
                gr.setUsername(rs.getString("username"));
                user.add(gr);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user.get(0);
    }

    public ArrayList<PrivateChat> privateChat(User Currentuser){
        ArrayList<PrivateChat> PrivateChatEntities = new ArrayList();
        String request = "select * from private_chat  where sender_id=? or received_id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1, Currentuser.getId());
            st.setInt(2, Currentuser.getId());
            ResultSet rs = st.executeQuery();

            while(rs.next()){

                PrivateChat prv=new PrivateChat();

                prv.setId(rs.getInt(1));
                prv.setSender(getUserById(rs.getInt("sender_id")));
                prv.setReceived(getUserById(rs.getInt("received_id")));

                PrivateChatEntities.add(prv);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return PrivateChatEntities;
    }

    GroupeChatService GrService = new GroupeChatService();

    public void AjouterPrivateChat (int id)

    {
        ArrayList<User> OtherUsers =GrService.getUsers(id);
        for (int i=0;i<OtherUsers.size();i++)
        {
            Conversation c = new Conversation("privatechat", "privatechat");
            AjouterConversation(c);
            int idC = getLastConversation().get(0).getId();
            String req = "INSERT INTO private_chat (id,sender_id,received_id) VALUES " + "(?,?,?)";
            try {
                PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(req);
                pst.setInt(1, idC);
                pst.setInt(2, id);
                pst.setInt(3,OtherUsers.get(i).getId());
                pst.executeUpdate();
                System.out.println("private chat inserer");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
