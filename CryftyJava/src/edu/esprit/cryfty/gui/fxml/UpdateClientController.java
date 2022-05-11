package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.service.user.ClientService;
import edu.esprit.cryfty.service.user.Session;
import edu.esprit.cryfty.service.user.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateClientController implements Initializable {
    @FXML
    private TextField txtfirstname;
    @FXML
    private TextField txtlastname;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtphone;
    @FXML
    private Button btnupdate;

    @FXML
    void updateprofil(ActionEvent event) throws IOException {
        ClientService userService = new ClientService();
        Client client = userService.getClientByIdTransaction(Session.getInstance().getCurrentUser().getId());
        client.setFirstName(txtfirstname.getText());
        client.setLastName(txtlastname.getText());
        client.setEmail(txtemail.getText());
        client.setPhoneNumber(Integer.parseInt(txtphone.getText()));

        ClientService clientService = new ClientService();
        clientService.updateProfil(client);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.hide();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Stage window5 = new Stage();
        window5.setScene(new Scene(tableViewParent));
        window5.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientService userService = new ClientService();
        Client client = userService.getClientByIdTransaction(Session.getInstance().getCurrentUser().getId());
        txtfirstname.setText(client.getFirstName());
        txtlastname.setText(client.getLastName());
        txtemail.setText(client.getEmail());
        txtphone.setText(String.valueOf(client.getPhoneNumber()));
    }
}
