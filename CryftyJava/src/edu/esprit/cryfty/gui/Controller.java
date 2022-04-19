package edu.esprit.cryfty.gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.entity.chat.Message;
import edu.esprit.cryfty.entity.chat.PrivateChat;
import edu.esprit.cryfty.service.chat.GroupeChatService;
import edu.esprit.cryfty.service.chat.MessageService;
import edu.esprit.cryfty.service.chat.PrivateChatService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private Circle boule;
    @FXML
    private SplitPane ListConversation;
    @FXML
    private Label addGroup;
    @FXML
    private AnchorPane titleListConv;
    private boolean bool =true;
    @FXML
    private SplitPane conv;
    @FXML
    private AnchorPane affichConv;
    @FXML
    private Label NomConv;
    @FXML
    private AnchorPane HeadConversation;
    @FXML
    private ImageView closeConv;
    @FXML
    private TextField TfieldMessage;
    @FXML
    private ImageView ImgSendMsg;

    @FXML
    private Pane PaneMsgs;
    @FXML
    private ScrollPane s;
    @FXML
    private ScrollPane SpListConv;
    @FXML
    private Pane PaneListConv;
    @FXML
    private ImageView imageDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[10];


        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("fxml/Item.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }
    boolean clearSelection =true;
    public void afficheListConversation(Event actionEvent)
    {
        if (actionEvent.getSource() == boule && bool ==true )
        {
            ListConversation.setVisible(true);
            bool=false;
            ListConversation(actionEvent);

        }
        else if(actionEvent.getSource() == boule && bool ==false )
        {

            ListConversation.setVisible(false);
            bool=true;
            //listView.getSelectionModel().clearSelection();
            conv.setVisible(false);
            clearSelection=false;
        }
    }
    public void ListConversation (Event actionEvent)
    {

        ArrayList<Conversation> nom = new ArrayList<>();


        VBox layout = new  VBox(nom.size());
        for (int j=0;j< Affichage_GR_chat().size();j++)
        {
            nom.add( Affichage_GR_chat().get(j));
            Label nomG = new Label(Affichage_GR_chat().get(j).getNom());
            layout.getChildren().add(nomG);
            int id =Affichage_GR_chat().get(j).getId();
            String nomgr=Affichage_GR_chat().get(j).getNom();
            nomG.setOnMouseClicked(event->{
                System.out.println(id);
                GetMesgsbyConv(id);
                conv.setVisible(true);
                NomConv.setText(nomgr);
            });

            ImgSendMsg.setOnMouseClicked(event->{
                SendMsg(id);
            });
            if(Affichage_GR_chat().get(j).getOwner().getId()==4)
            {
                imageDelete.setVisible(true);
                layout.getChildren().add(imageDelete);
                //updateGroup(Affichage_GR_chat().get(j));
                deleteGroup(Affichage_GR_chat().get(j));

            }

        }
        for (int j=0;j<Affichage_private_chat().size();j++)
        {
            nom.add(Affichage_private_chat().get(j));
            if(Affichage_private_chat().get(j).getReceived().getId()==4)
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getSender().getUsername());
                layout.getChildren().add(nomP );
                Line l =new Line();
                int id =Affichage_private_chat().get(j).getId();
                String nompr=Affichage_private_chat().get(j).getSender().getUsername();
                nomP.setOnMouseClicked(event->{
                GetMesgsbyConv(id);
                conv.setVisible(true);
                NomConv.setText(nompr);
                });
                ImgSendMsg.setOnMouseClicked(event->{
                    SendMsg(id);
                });
                //layout.getChildren().add(l);
            }
            else
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getReceived().getUsername());
                System.out.println(Affichage_private_chat().get(j).getSender().getUsername());
                layout.getChildren().add(nomP);
                Line l =new Line();
             //   layout.getChildren().add(l);
                int id =Affichage_private_chat().get(j).getId();
                String nompr=Affichage_private_chat().get(j).getReceived().getUsername();
                nomP.setOnMouseClicked(event->{
                    GetMesgsbyConv(id);
                    conv.setVisible(true);
                    NomConv.setText(nompr);
                });
                ImgSendMsg.setOnMouseClicked(event->{
                    SendMsg(id);
                });
            }

        }
        SpListConv.setContent(layout);



    }

    public ArrayList<PrivateChat>  Affichage_private_chat()
    {
    ArrayList<PrivateChat > PrivateChat = new ArrayList<>();
    PrivateChatService prvService = new PrivateChatService();
    // user connecté
    User u=   prvService.getUserById(4);
    ArrayList<PrivateChat> privateChat = prvService.privateChat(u);
    for (int p=0;p<privateChat.size();p++)
    {
            PrivateChat.add(privateChat.get(p));
    }
    return PrivateChat;
}
    public ArrayList<GroupeChat>  Affichage_GR_chat()
    {  GroupeChatService GrService= new GroupeChatService();
        ArrayList<GroupeChat> Groups= GrService.getGroups();
        ArrayList<GroupeChat > nomGroup = new ArrayList<>();
        for(int i=0;i<Groups.size();i++)
        {
            ArrayList <User> Participants = Groups.get(i).getParticipants();
            for (int j=0;j<Participants.size();j++)
            {
                if(Participants.get(j).getId()==4)
                {
                    nomGroup.add(Groups.get(i));
                }
            }
            if(Groups.get(i).getOwner().getId()==4)
            {
                nomGroup.add(Groups.get(i));
            }
        }return nomGroup;
    }

    @FXML
    public void closeConv(Event event) {
        conv.setVisible(false);
    }

    public void GetMesgsbyConv(int id )
    {
        MessageService msgService = new MessageService();
       ArrayList<Message> msgs=msgService.getMessageByCon(msgService.getConversationById(id));
        VBox layout = new  VBox(msgs.size());
        layout.setStyle("-fx-pref-width:145;");
        for(int i=0;i<msgs.size();i++) {
            Message m = msgs.get(i);
            if (m.getSender().getId() != 4) {

                Label SenderLaber = new Label(m.getSender().getUsername());
                layout.getChildren().add(SenderLaber);

                Label ContenuLabel = new Label(m.getContenu());
                ContenuLabel.setStyle("-fx-border-radius : 15");
                layout.getChildren().add(ContenuLabel);

                Label DateLaber = new Label(m.getCreatedAt());
                DateLaber.setStyle("-fx-font-family:'Robotom Medium'; ");
                layout.getChildren().add(DateLaber);

                Label SeparatorLabel = new Label("\n");
                // layout.getChildren().add(SeparatorLabel);
               SenderLaber.setPadding(new Insets(5, 80, 0, 0));
                ContenuLabel.setAlignment(Pos.BASELINE_RIGHT);
                DateLaber.setAlignment(Pos.BASELINE_RIGHT);
                SenderLaber.setAlignment(Pos.BASELINE_RIGHT);


            }else if(m.getSender().getId() == 4)
            {
                System.out.println("test other User"+m.getSender().getId());
                Label SenderLaber = new Label(m.getSender().getUsername());
                layout.getChildren().add(SenderLaber);

                Label ContenuLabel = new Label(m.getContenu());
                ContenuLabel.setStyle("-fx-border-radius : 15");

                layout.getChildren().add(ContenuLabel);

                Label DateLaber = new Label(m.getCreatedAt());
                DateLaber.setStyle("-fx-font-family:'Robotom Medium'; ");
                layout.getChildren().add(DateLaber);

                Label SeparatorLabel = new Label("\n");
               //layout.getChildren().add(SeparatorLabel);
               SenderLaber.setPadding(new Insets(5, 0, 0, 80));
                ContenuLabel.setPadding(new Insets(0, 0, 0, 80));
                DateLaber.setPadding(new Insets(0, 0, 0, 30));
                //SeparatorLabel.setPadding(new Insets(0, 0, 0, 80));
                //layout.setStyle("-fx-border-color:black");

            }
        }
       s.setContent(layout);

    }

    @FXML
    public void addGroup(Event event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("fxml/AddGroup.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public  void  SendMsg(int id)
    {

        MessageService MsgService =new MessageService();
         Conversation c= MsgService.getConversationById(id);
         //Current User
         User u = MsgService.getUserById(4);

        Message msg = new Message(TfieldMessage.getText(),c,u);
        MsgService.SendMsg(msg);
    }

    public void deleteGroup(Conversation conversation)
    {
        GroupeChatService GrService= new GroupeChatService();
        imageDelete.setOnMouseClicked(event->{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure to delete this nft from your cart?");
            Optional<ButtonType> action =alert.showAndWait();
            if(action.get()== ButtonType.OK)
            {
                GrService.deleteGroup(conversation);
            }

        });
    }
    public void updateGroup(GroupeChat Grchat)
    {
        GroupeChatService GrService= new GroupeChatService();

        NomConv.setOnKeyPressed(event->{
               // GroupeChat gr =new GroupeChat(NomConv.getText());
                    GrService.updateConversation(Grchat);
                });
    }

}
