package edu.esprit.cryfty.gui.fxml;

import com.itextpdf.text.DocumentException;
import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.User.Reclamation;
import edu.esprit.cryfty.service.user.ClientService;
import edu.esprit.cryfty.service.user.ReclamationService;
import edu.esprit.cryfty.service.user.Session;
import edu.esprit.cryfty.utils.ServicePdf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.net.URL;
import java.security.PublicKey;
import java.sql.*;
import java.util.Observable;
import java.util.ResourceBundle;

public class ReclamationController implements Initializable  {
    @FXML
    private Button btnpdf;
    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<Reclamation, String> emailcolmn;

    @FXML
    private TableColumn<Reclamation, Integer> idcolmn;

    @FXML
    private TableColumn<Reclamation, String> messagecolmn;

    @FXML
    private TableColumn<Reclamation, String> namecolmn;

    @FXML
    private TableColumn<Reclamation, String> subjectcolmn;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfmessage;

    @FXML
    private TextField tfname;

    @FXML
    private TextField tfsubject;
    @FXML
    private TextField txfid;

    @FXML
    private TableView<Reclamation> tvreclamation;

    @FXML
    void add(ActionEvent event) {
        if (tfname.getText().isEmpty() || tfsubject.getText().isEmpty() || tfmessage.getText().isEmpty() || tfemail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalide champ");
            System.out.println("test");
        } else {
            int iduser = LoginController.idglobaluser;
            String email = tfemail.getText();
            String subject = tfsubject.getText();
            String message = tfmessage.getText();
            String name = tfname.getText();
            System.out.println(iduser);
            Reclamation cl = new Reclamation(1, email, subject, message, name,iduser);
            ReclamationService clientSrv = new ReclamationService();
            clientSrv.addReclamation(cl);
            showReclamation();

        }
    }



    @FXML
    void delete(ActionEvent event) {
        String request = "DELETE FROM support_ticket WHERE id=" + txfid.getText() + "";
        executeQuery(request);
        showReclamation();



    }
    private void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) {
        int id =Integer.parseInt(txfid.getText());
        String email = tfemail.getText();
        String subject  = tfsubject.getText();
        String message = tfmessage.getText();
        String name = tfname.getText();
        Reclamation cl = new Reclamation(id,email, subject, message,name);
        ReclamationService clientSrv = new ReclamationService();
        clientSrv.updateReclamaton(cl);
        showReclamation();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showReclamation();
        System.out.println(Session.getInstance().getCurrentUser().getUsername());

    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cryfty","root","");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
            return null;

        }


    }


    public ObservableList<Reclamation>getReclamationList(){
        ObservableList<Reclamation>reclamationList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query="select * from support_ticket";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs=st.executeQuery(query);
            Reclamation reclamations;
            while (rs.next()){
                reclamations = new Reclamation(rs.getInt("id"),rs.getString("email"),rs.getString("subject"),rs.getString("message"),rs.getString("name"));
                reclamationList.add(reclamations);
            }
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return reclamationList;
    }

    public void showReclamation(){
        ObservableList<Reclamation> list = getReclamationList();
        idcolmn.setCellValueFactory(new PropertyValueFactory<Reclamation,Integer>("id"));
        emailcolmn.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("email"));
        subjectcolmn.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("subject"));
        messagecolmn.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("message"));
        namecolmn.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("name"));

        tvreclamation.setItems(list);



    }

    @FXML
    void handleMouseAction(MouseEvent event) {
       Reclamation reclamation= tvreclamation.getSelectionModel().getSelectedItem();
       System.out.println("id"+reclamation.getId());
        System.out.println("email"+reclamation.getEmail());
        System.out.println("subject"+reclamation.getSubject());
        System.out.println("message"+reclamation.getMessage());
        System.out.println("name"+reclamation.getName());





    }


    public void handleMouseAction(javafx.scene.input.MouseEvent mouseEvent) {
        Reclamation reclamation= tvreclamation.getSelectionModel().getSelectedItem();
        txfid.setText(""+reclamation.getId());
        tfemail.setText(reclamation.getEmail());
        tfsubject.setText(reclamation.getSubject());
        tfmessage.setText(reclamation.getMessage());
        tfname.setText(reclamation.getName());
    }



    @FXML
    private void pdf(ActionEvent event) throws FileNotFoundException, DocumentException {
        ServicePdf sp= new ServicePdf();
        sp.equipePDF();
    }
}
