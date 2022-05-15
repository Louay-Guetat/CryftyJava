package edu.esprit.cryfty.service.chat;

import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.Message;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;

public class MessageService {

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

    public void SendMsg(Message msg,Conversation c)

    {
        String req= "INSERT INTO message (sender_id,conversation_id,contenu,created_at) VALUES "+"(?,?,?,?)";
        try {
            PreparedStatement pst= DataSource.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, msg.getSender().getId());
            pst.setInt(2, c.getId());
            pst.setString(3, msg.getContenu());
            pst.setObject(4,msg.getCreatedAt());
            pst.executeUpdate();
            System.out.println("Message inserer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public Conversation getConversationById(int id) {
        ArrayList<Conversation> conversation = new ArrayList();
        String request = "select id ,nom,type from conversation where id = ?" ;
        try {
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Conversation gr = new Conversation();
                gr.setId(rs.getInt(1));
                gr.setNom(rs.getString("nom"));
                gr.setType(rs.getString("type"));
                conversation.add(gr);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conversation.get(0);
    }

    public ArrayList<Message> getMessageByCon(Conversation c){
        ArrayList<Message> MessageEntities = new ArrayList();
        String request = "select * from message where conversation_id = ? ";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1,c.getId());

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                Message msg=new Message();
                msg.setId(rs.getInt(1));
                msg.setCreatedAt((rs.getString("created_at")));
                msg.setContenu((rs.getString("contenu")));
                msg.setSender(getUserById(rs.getInt("sender_id")));
                msg.setConversation(getConversationById(rs.getInt("conversation_id")));
                //st.setInt(2,rs.getInt(1));
                MessageEntities.add(msg);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return MessageEntities;
    }

    public ArrayList<Message> getMessageById(int  id){
        ArrayList<Message> MessageEntities = new ArrayList();
        String request = "select * from message where   id = ?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1,id);

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                Message msg=new Message();
                msg.setId(rs.getInt(1));
                msg.setCreatedAt(rs.getString("created_at"));
                msg.setContenu((rs.getString("contenu")));
                msg.setSender(getUserById(rs.getInt("sender_id")));
                msg.setConversation(getConversationById(rs.getInt("conversation_id")));
                //st.setInt(2,rs.getInt(1));
                MessageEntities.add(msg);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return MessageEntities;
    }
    public void deleteMessage(Message msg){
        String request = "delete from message where id = ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1, msg.getId());
            if(pst.executeUpdate()==0)
                System.out.println("message does not exist");
            else
                System.out.println("message removed");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Message> getLastMessageByCon(Conversation c){
        ArrayList<Message> MessageEntities = new ArrayList();
        String request = "select * from message where conversation_id = ?  ORDER BY created_at DESC limit 1";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1,c.getId());

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                Message msg=new Message();
                msg.setId(rs.getInt(1));
                msg.setCreatedAt((rs.getString("created_at")));
                msg.setContenu((rs.getString("contenu")));
                msg.setSender(getUserById(rs.getInt("sender_id")));
                msg.setConversation(getConversationById(rs.getInt("conversation_id")));
                //st.setInt(2,rs.getInt(1));
                MessageEntities.add(msg);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return MessageEntities;
    }

}
