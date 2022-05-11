package edu.esprit.cryfty.gui.fxml;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.service.user.ClientService;
import javafx.stage.Stage;

import javax.swing.*;


public class SignupController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnvalider;

    @FXML
    private TextField tfaddress;

    @FXML
    private TextField tfage;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tffirstname;

    @FXML
    private TextField tflastname;

    @FXML
    private TextField tfpaasword;

    @FXML
    private TextField tfphonenumber;

    @FXML
    private TextField tfrole;

    @FXML
    private TextField tfusername;
    @FXML
    private Button idtologin;

    @FXML
    void saveclient(ActionEvent event) throws IOException, AWTException {

        if (tfusername.getText().isEmpty() || tffirstname.getText().isEmpty()|| tflastname.getText().isEmpty()|| tfemail.getText().isEmpty()|| tfaddress.getText().isEmpty()|| tfpaasword.getText().isEmpty()) {
            if (tfusername.getText().length() < 4) {
                JOptionPane.showMessageDialog(null, "Your username must contain at least 4 characters \\n\"");
            } else {
                JOptionPane.showMessageDialog(null, "Invalide champ");
            }
            if (tffirstname.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "Your firstname must contain at least 3 characters \\n\"");}
            if (tflastname.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "Your lastname must contain at least 3 characters \\n\"");}
        } else {
            String username = tfusername.getText();
            String firstname = tffirstname.getText();
            String lastname = tflastname.getText();
            String email = tfemail.getText();
            int phonenumber = Integer.parseInt(tfphonenumber.getText());
            String address = tfaddress.getText();
            String password = tfpaasword.getText();
            int age = Integer.parseInt(tfage.getText());

            System.out.println("test");

            Client cl = new Client(1, username, password, firstname, lastname, email, phonenumber, age, address, "h", "h");
            ClientService clientSrv = new ClientService();
            clientSrv.addClient(cl);

            //Notiffication Dec
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            trayIcon.displayMessage("Notification ajout", "Client ajoutée avec succès", TrayIcon.MessageType.INFO);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.hide();
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage window5 = new Stage();
            window5.setScene(new Scene(tableViewParent));
            window5.show();

        }
    }

    public void tologinsc(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("login.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
