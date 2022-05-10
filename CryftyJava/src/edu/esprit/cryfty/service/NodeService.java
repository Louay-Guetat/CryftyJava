package edu.esprit.cryfty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.esprit.cryfty.entity.Coin;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.utils.DataSource;
import edu.esprit.cryfty.utils.ReadJson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class NodeService {

    public ObservableList<Node> getNodes() {
        String request = "select * from node";
        ObservableList<Node> nodes = FXCollections.observableArrayList();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Node node = new Node();
                node.setId(rs.getInt("id"));
                node.setNodeLabel(rs.getString("node_label"));
                node.setCoinCode(rs.getString("coin_code"));
                node.setNodeReward(rs.getDouble("node_reward"));
                nodes.add(node);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nodes;
    }

    public ObservableList<Coin> getCoinsValue() throws IOException {

        String url = "https://api.coingecko.com/api/v3/exchange_rates/";
        ReadJson reader = new ReadJson();
        JSONObject json = reader.readJsonFromUrl(url);
        JSONObject jsonObject = json.getJSONObject("rates");
        Map<String, LinkedHashMap> map = new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
        ObservableList<Coin> coins = FXCollections.observableArrayList();
        for (LinkedHashMap<String,Object> str : map.values()) {
            Coin coin = new Coin();
            coin.setName((String) str.get("name"));
            coin.setType((String) str.get("type"));
            coin.setUnit((String) str.get("unit"));
            coin.setValue(String.valueOf(str.get("value")));
            System.out.println(coin.getValueDouble());
            coins.add(coin);
        }
        System.out.println(coins);

        return coins;
    }

    public Map<String,Integer> getStats(){
        Map<String,Integer> nodeListMap = new HashMap<>();
        String request = "select node_id ,count(*) as nmbr from block GROUP BY node_id";
        try {
            PreparedStatement statement = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                System.out.println(resultSet.getInt("node_id"));
                nodeListMap.put(getNodeById(resultSet.getInt("node_id")).getNodeLabel(),resultSet.getInt("nmbr"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(nodeListMap);
        return nodeListMap;
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
