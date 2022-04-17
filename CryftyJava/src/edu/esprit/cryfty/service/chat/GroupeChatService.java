package edu.esprit.cryfty.service.chat;

import edu.esprit.cryfty.entity.*;
import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class GroupeChatService {
    /* UserService userService;

    public GroupeChatService(UserService userService) {
        this.userService = userService;
    }

*/ public User getUserById(int id) {
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
    public void Ajoutergroup_chat_user(GroupeChat gr, int idC)
    {
        //Conversation c = new Conversation(gr.getNom(),"groupchat");
        for(int i =0;i<gr.getParticipants().size();i++){
        String req= "INSERT INTO group_chat_user (group_chat_id,user_id) VALUES "+"(?,?)";
        try {
            PreparedStatement pst= DataSource.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, idC);
            pst.setInt(2, gr.getParticipants().get(i).getId());
            pst.executeUpdate();
            System.out.println("groupe_chat_user inserer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        }
    }
    public ArrayList<User> getParticipantsById(int id) {
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

        return user;
    }

    public String getNomConversationById(int id) {
        ArrayList<String> conversation= new ArrayList();
        String request = "select nom from conversation where id ="+id ;
        try {
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
               Conversation gr = new Conversation();

                gr.setNom(rs.getString("nom"));
                conversation.add(gr.getNom());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conversation.get(0);
    }

    public GroupeChatService() {
    }

    public ArrayList<GroupeChat> getGroups(){
        ArrayList<GroupeChat> GroupEntities = new ArrayList();
        String request = "select * from group_chat  ";
        try{
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(request);

            while(rs.next()){

                GroupeChat gr=new GroupeChat();
                gr.setId(rs.getInt(1));

                gr.setNom(getNomConversationById(rs.getInt(1)));
                gr.setOwner(getUserById(rs.getInt("owner_id")));
                gr.setParticipants(getParticipantsByGr(gr.getId()));
                GroupEntities.add(gr);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return GroupEntities;
    }
//Affiche les participants par group
    public ArrayList<User> getParticipantsByGr(int id){
        ArrayList<User> ParticipantsEntities = new ArrayList();
        String request = "select user_id from  group_chat_user where group_chat_id= ?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while(rs.next()){

                User Participants=new User();
                Participants.setId(rs.getInt(1));
                Participants.setUsername(getParticipantsById(rs.getInt("user_id")).get(0).getUsername());
                ParticipantsEntities.add(Participants);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return ParticipantsEntities;
    }

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

    public void AjouterGroupe(GroupeChat gr)
    {
        Conversation c = new Conversation(gr.getNom(),"groupchat");
        AjouterConversation(c);
        int idC = getLastConversation().get(0).getId();
        String req= "INSERT INTO group_chat (id,owner_id) VALUES "+"(?,?)";
        try {
            PreparedStatement pst= DataSource.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, idC);
            pst.setInt(2, gr.getOwner().getId());
            pst.executeUpdate();
            Ajoutergroup_chat_user(gr,idC );
            System.out.println("groupe chat inserer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
    public void deleteGroup(Conversation gr){
        String request = "delete from conversation where id = ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1, gr.getId());
            if(pst.executeUpdate()==0)
                System.out.println("group does not exist");
            else
                System.out.println("group removed");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateConversation (Conversation c){
        String req = "update conversation set nom=? where id=?";
        //updateParticipantsGr(c);
        try {
            PreparedStatement  pst = DataSource.getInstance().getCnx().prepareStatement(req);

            pst.setString(1, c.getNom());
            pst.setInt(2, c.getId());
            System.out.println("group updated");
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateParticipantsGr (Conversation c){

       GroupeChat  gr = (GroupeChat)c;

        for(int i =0;i<gr.getParticipants().size();i++)
    {
        String req = "update group_chat_user set user_id=? where id=?";
        try {
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, gr.getParticipants().get(i).getId());
            // pst.setString(1, c.getNom());
            pst.setInt(2, c.getId());
            System.out.println("Participants updeted");
            pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    }



}
