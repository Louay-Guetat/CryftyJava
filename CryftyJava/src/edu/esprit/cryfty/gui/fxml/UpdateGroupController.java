package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.service.chat.GroupeChatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class UpdateGroupController  implements Initializable {
    @FXML
    private Pane PaneUpdate;
    @FXML
    private TableView<User> tabUser;
    @FXML
    private Button update;
    @FXML
    private TextField NomG;
    @FXML
    private Label MsgError;
    @FXML
    private TableColumn<User,String> idUser;
    @FXML
    private TableColumn<User,String> nameUser;
    @FXML
    private TableColumn<User,CheckBox>  select;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    NomG.setText(AffichParticipantsByGroupController.UpdateClicked.getNom());
       checkboxUser();
    }
    ObservableList<User> list = FXCollections.observableArrayList();
   public void checkboxUser()
    {

        GroupeChatService groupeChatService = new GroupeChatService();
        ArrayList<User> OtherUsers = groupeChatService.afficheUsersMinusParticp(AffichParticipantsByGroupController.UpdateClicked.getId(),AffichParticipantsByGroupController.UpdateClicked.getOwner().getId());
        for(int i=0;i<OtherUsers.size();i++)
        {
            CheckBox checkbox = new CheckBox("");
            list.add(new User(OtherUsers.get(i).getId(),OtherUsers.get(i).getUsername(),checkbox));
            System.out.println(OtherUsers.get(i).getUsername());
        }
        tabUser.setItems(list);
      nameUser.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
       select.setCellValueFactory(new PropertyValueFactory<User,CheckBox>("select"));
      idUser.setCellValueFactory(new PropertyValueFactory<User,String>("id"));


    }
    public void SelectItems2()
    {
        ArrayList<User> Participants = new ArrayList<>();
        GroupeChatService groupeChatService = new GroupeChatService();

        for(User u2 : tabUser.getItems())
        {
            if(u2.getSelect().isSelected())
            {
                System.out.println(u2.getId());
                Participants.add(u2);
            }
        }
        User currentUser=  groupeChatService.getUserById(4);
        String name = NomG.getText();

        if(name.equals("") ) {
            MsgError.setVisible(true);
            MsgError.setText("Name Group is Required");
        }
        else
        {
            MsgError.setVisible(false);
            System.out.println(name);
            GroupeChat group = new GroupeChat(name, currentUser, Participants);
            AffichParticipantsByGroupController.UpdateClicked.setNom(name);
            groupeChatService.updateConversation(AffichParticipantsByGroupController.UpdateClicked);
            groupeChatService.Ajoutergroup_chat_user(group,AffichParticipantsByGroupController.UpdateClicked.getId());


        }
    }

    public Pane getPaneUpdate() {
        return PaneUpdate;
    }

    public void setPaneUpdate(Pane paneUpdate) {
        PaneUpdate = paneUpdate;
    }

    public TableView<User> getTabUser() {
        return tabUser;
    }

    public void setTabUser(TableView<User> tabUser) {
        this.tabUser = tabUser;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public TextField getNomG() {
        return NomG;
    }

    public void setNomG(TextField nomG) {
        NomG = nomG;
    }

    public Label getMsgError() {
        return MsgError;
    }

    public void setMsgError(Label msgError) {
        MsgError = msgError;
    }

    public TableColumn<User, String> getIdUser() {
        return idUser;
    }

    public void setIdUser(TableColumn<User, String> idUser) {
        this.idUser = idUser;
    }

    public TableColumn<User, String> getNameUser() {
        return nameUser;
    }

    public void setNameUser(TableColumn<User, String> nameUser) {
        this.nameUser = nameUser;
    }

    public TableColumn<User, CheckBox> getSelect() {
        return select;
    }

    public void setSelect(TableColumn<User, CheckBox> select) {
        this.select = select;
    }

    public ObservableList<User> getList() {
        return list;
    }

    public void setList(ObservableList<User> list) {
        this.list = list;
    }

}
