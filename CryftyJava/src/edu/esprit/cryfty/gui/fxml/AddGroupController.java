package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.service.chat.GroupeChatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable {
    @javafx.fxml.FXML
    private Pane Formulaire;
    @javafx.fxml.FXML
    private TextField TfieldNameGroup;
    @javafx.fxml.FXML
    private Button btnAddGroup;
    @javafx.fxml.FXML
    private TableView<User> tabUsers;
    @javafx.fxml.FXML
    private TableColumn<User,String> nameUser;
    @javafx.fxml.FXML
    private TableColumn<User,CheckBox> select;
    @javafx.fxml.FXML
    private TableColumn<User,String> idUser;
    @javafx.fxml.FXML
    private Label msgError;
    @javafx.fxml.FXML
    private Label msgErrorCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkboxUser();

    }

    public void AddGroup ()
    {
        GroupeChatService groupeChatService = new GroupeChatService();
    }
    ObservableList<User> list = FXCollections.observableArrayList();
    public void checkboxUser()
    {

        GroupeChatService groupeChatService = new GroupeChatService();
        ArrayList<User> OtherUsers = groupeChatService.getUsers(4);
        for(int i=0;i<OtherUsers.size();i++)
        {
            CheckBox checkbox = new CheckBox("");
            list.add(new User(OtherUsers.get(i).getId(),OtherUsers.get(i).getUsername(),checkbox));
            System.out.println(OtherUsers.get(i).getUsername());
        }
         tabUsers.setItems(list);
        tabUsers.setStyle("-fx-border-radius:2em");
        getSelect().setStyle("-fx-background-color:#778899;");
        getNameUser().setStyle("-fx-background-color:#778899;-fx-text-fill:black;-fx-font-weight:bold");
         nameUser.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
         select.setCellValueFactory(new PropertyValueFactory<User,CheckBox>("select"));
         idUser.setCellValueFactory(new PropertyValueFactory<User,String>("id"));


    }
    public void SelectItems( ActionEvent e)
        {
            ArrayList<User> Participants = new ArrayList<>();
            GroupeChatService groupeChatService = new GroupeChatService();

            for(User u : tabUsers.getItems())
            {
                if(u.getSelect().isSelected())
                {
                    System.out.println(u.getId());
                    Participants.add(u);
                }
            }
            User currentUser=  groupeChatService.getUserById(4);
            String name = TfieldNameGroup.getText();
            msgError.setStyle("-fx-text-fill: red;");
            msgErrorCheck.setStyle("-fx-text-fill: red;");
            if(name.equals("") && Participants.size()<2)
            {
                msgError.setVisible(true);
                msgError.setText("Name Group is Required");
                msgErrorCheck.setVisible(true);
                msgErrorCheck.setText("Check min 2 users");
            }else if((name.equals("")))
            {
                msgError.setVisible(true);
                msgError.setText("Name Group is Required");
                msgErrorCheck.setVisible(false);
            }else if( (Participants.size()<2))
            {
                msgError.setVisible(false);
                msgErrorCheck.setText("Check min 2 users");
                msgErrorCheck.setVisible(true);
            }

           else
           {
                msgError.setVisible(false);
                msgErrorCheck.setVisible(false);
                GroupeChat group = new GroupeChat(name, currentUser, Participants);
                groupeChatService.AjouterGroupe(group);

               Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
               window.hide();


            }
        }
    public TableView getTabUsers() {
        return tabUsers;
    }

    public void setTabUsers(TableView tabUsers) {
        this.tabUsers = tabUsers;
    }

    public TableColumn getNameUser() {
        return nameUser;
    }

    public void setNameUser(TableColumn nameUser) {
        this.nameUser = nameUser;
    }

    public TableColumn getSelect() {
        return select;
    }

    public void setSelect(TableColumn select) {
        this.select = select;
    }

    public TextField getTfieldNameGroup() {
        return TfieldNameGroup;
    }

    public void setTfieldNameGroup(TextField tfieldNameGroup) {
        TfieldNameGroup = tfieldNameGroup;
    }

    public Button getBtnAddGroup() {
        return btnAddGroup;
    }

    public void setBtnAddGroup(Button btnAddGroup) {
        this.btnAddGroup = btnAddGroup;
    }



}
