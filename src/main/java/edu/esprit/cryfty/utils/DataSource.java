package edu.esprit.cryfty.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
        private String url = "jdbc:mysql://127.0.0.1:3306/cryfty";      //localhost ou 127.0.0.1
        private String login= "root";
        private String pwd= "";
        private Connection cnx;
        public static DataSource instance; //2eme etape singleton

        private DataSource() { // pour appliquer le singleton, on rend le constructeur private 1ere etape
            try{
                cnx = DriverManager.getConnection(url, login, pwd);
                System.out.println("Connection etablie.");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

        public Connection getCnx(){
            return cnx;
        }

        public static DataSource getInstance(){     // 3eme etape singleton
            if(instance == null){
                instance = new DataSource();
            }
            else{
                return instance;
            }
            return instance;
        }

}
