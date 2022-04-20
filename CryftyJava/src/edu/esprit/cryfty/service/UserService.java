//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.service;

import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.utils.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {
    public UserService() {
    }

    public void addUser(String username, String roles, String password) {
        String request = "insert into user(username,roles,password,type,is_active) values(?,?,?,?,0)";

        try {
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1, username);
            pst.setString(2, roles);
            pst.setString(3, password);
            pst.setString(4, "client");
            pst.executeUpdate();
            System.out.println("Utilisateur ajoute");
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }

    }

    public User showUser() {
        User user = new User();
        String request = "SELECT * FROM user ORDER BY ID DESC LIMIT 1";

        try {
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            rs.next();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setRoles(rs.getString("roles"));
            user.setPassword(rs.getString("password"));
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return user;
    }

    public User findUserById(int id) {
        User user = new User();
        String request = "select * from user where id=" + id;

        try {
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);

            while(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }

        return user;
    }
}
