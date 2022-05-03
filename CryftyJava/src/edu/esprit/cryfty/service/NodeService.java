package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NodeService {

    public ArrayList<Node> getNodes() {
        String request = "select * from node";
        ArrayList<Node> nodes = new ArrayList<>();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Node node = new Node();
                node.setId(rs.getInt(1));
                node.setNodeLabel(rs.getString("node_label"));
                node.setCoinCode(rs.getString("coin_code"));
                node.setNodeReward(rs.getDouble("node_reward"));
                System.out.println(node);
                nodes.add(node);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nodes;
    }

    public void addNod(Node node){
        String request = "insert into node(node_label,node_reward,coin_code) " +
                "VALUES (?,?,?)";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, node.getNodeLabel());
            st.setDouble(2, node.getNodeReward());
            st.setString(3, node.getCoinCode());
            st.executeUpdate();
            System.out.println("Node Added.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


    public void updateNode(Node node){
        String request = "update node set node_label=? , node_reward=? , coin_code=? where id=?";
        try{
            PreparedStatement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.setString(1, node.getNodeLabel());
            st.setString(3, node.getCoinCode());
            st.setDouble(2, node.getNodeReward());
            st.setInt(4, node.getId());
            st.executeUpdate();
            System.out.println("Wallet Updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteNode(Node node){
        String request = "delete from node where id="+node.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Node deleted Successfully");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void deleteNode(int id){
        String request = "delete from node where id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Node deleted Successfully");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Node getNodeById(int id) {
        String request = "select * from node where id="+id;
        Node node = new Node();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                node.setId(rs.getInt(1));
                node.setNodeLabel(rs.getString("node_label"));
                node.setCoinCode(rs.getString("coin_code"));
                node.setNodeReward(rs.getDouble("node_reward"));
                System.out.println(node);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return node;

    }

    public Node getNodeByName(String coinCode){
        Node node = new Node();
        String request = "select * from node where coin_code like '"+coinCode+"'";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                node.setId(rs.getInt("id"));
                node.setNodeLabel(rs.getString("node_label"));
                node.setCoinCode(rs.getString("coin_code"));
                node.setNodeReward(rs.getInt("node_reward"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return node;

    }
}