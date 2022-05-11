package edu.esprit.cryfty.gui.fxml;


import edu.esprit.cryfty.entity.User.Reclamation;
import edu.esprit.cryfty.service.user.ReclamationService;
import edu.esprit.cryfty.service.user.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class AddreclamationController {
    @FXML
    private Button btnadd;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtsubject;
    @FXML
    private TextField txtmessage;

    @FXML
    void add(ActionEvent event) throws IOException {
        if (txtname.getText().isEmpty() || txtsubject.getText().isEmpty() || txtmessage.getText().isEmpty() || txtemail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalide champ");
            System.out.println("test");
        } else {
            String email = txtemail.getText();
            String subject = txtsubject.getText();
            String message = txtmessage.getText();
            String name = txtname.getText();
            Reclamation cl = new Reclamation(1, email, subject, message, name, Session.getInstance().getCurrentUser().getId());
            ReclamationService clientSrv = new ReclamationService();
            clientSrv.addReclamation(cl);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.hide();
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Stage window5 = new Stage();
            window5.setScene(new Scene(tableViewParent));
            window5.show();
        }
    }
}
