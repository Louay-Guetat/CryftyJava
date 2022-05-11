package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.service.user.ClientService;
import edu.esprit.cryfty.service.user.Session;
import edu.esprit.cryfty.service.user.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public static int idglobaluser;
    UserService su = new UserService();

    @FXML
    private Button inscrit;

    @FXML
    private Button btnok;

    @FXML
    private PasswordField txtpass;

    @FXML
    private TextField txtusername;

    @FXML
    void login(ActionEvent event) {

        String username = txtusername.getText();
        String pass = txtpass.getText();
        if(username.equals("")&& pass.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password Blank");
        }


    }
    @FXML
    void PageInscrit(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("fxml/Home.fxml")));
        stage.setScene(scene);
        stage.show();

    }

    public void login(javafx.event.ActionEvent actionEvent) throws IOException {
        String username = txtusername.getText();
        String pass = txtpass.getText();
        if(username.equals("")&& pass.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password Blank");
        }
        else {
                ClientService sc = new ClientService();
                if (sc.verifPassword(username, pass)) {
                    idglobaluser=su.findIdByEmail(txtusername.getText());
                    User currentUser = su.getByidd(idglobaluser);
                    Session.getInstance().setCurrentUser(currentUser);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alerte");
                    alert.setHeaderText(null);
                    alert.setContentText("!!! welcome  !!!");
                    alert.showAndWait();

                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.hide();
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage window5 = new Stage();
                window5.setScene(new Scene(tableViewParent));
                window5.show();

            }  else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte");
                alert.setHeaderText(null);
                alert.setContentText("!!! Verifiez Vos Coordonnees !!!");
                alert.showAndWait();
            }
        }
    }

    public void PageInscrit(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Signup.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
