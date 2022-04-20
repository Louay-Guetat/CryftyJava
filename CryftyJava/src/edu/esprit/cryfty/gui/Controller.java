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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Label MsgErrorInputMsg;


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
            conv.setVisible(false);

        }
    }
    FontAwesomeIconView Peoples = new FontAwesomeIconView(FontAwesomeIcon.USERS);
    public static GroupeChat GrClicked =null;
    public void ListConversation (Event actionEvent)
    {
        ArrayList<Conversation> nom = new ArrayList<>();
        VBox layout = new  VBox(nom.size());
        for (int j=0;j< Affichage_GR_chat().size();j++)
        {
            nom.add( Affichage_GR_chat().get(j));
            Label nomG = new Label(Affichage_GR_chat().get(j).getNom());
            layout.getChildren().add(nomG);
          GroupeChat  gr=Affichage_GR_chat().get(j);
            int id =Affichage_GR_chat().get(j).getId();
            //System.out.println("idconv"+GrClicked2.getId());
            String nomgr=Affichage_GR_chat().get(j).getNom();
            Conversation g=Affichage_GR_chat().get(j);
            nomG.setOnMouseClicked(event->{
                System.out.println(id);
                GetMesgsbyConv(id);
                conv.setVisible(true);
                NomConv.setText(nomgr);
                ImgSendMsg.setOnMouseClicked(event2->{
                   // GrClicked2 = gr;
                    System.out.println("yyy"+id);
                    SendMsg(id);});
                HBox hbox=new HBox(Peoples);
                hbox.setStyle("-fx-alignement:right;-fx-color:white");
                hbox.setMargin(Peoples,new Insets(5, 0, 0, 130));
                HeadConversation.getChildren().add(hbox);
                Peoples.setStyle("-fx-fill:white;");
                Peoples.setVisible(true);
                Peoples.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Stage primaryStage = new Stage();
                        Parent root = null;
                         GrClicked = gr;
                        try {
                          root = FXMLLoader.load(getClass().getResource("fxml/AffichParticipantsByGroup.fxml"));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        primaryStage.setScene(new Scene( root));
                        primaryStage.show();
                    }
                });
            });
            if(Affichage_GR_chat().get(j).getOwner().getId()==4)
            {
                FontAwesomeIconView deleteIconGroup = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                HBox hbox=new HBox(deleteIconGroup);
                hbox.setStyle("-fx-alignement:right;");
                hbox.setMargin(deleteIconGroup,new Insets(0, 0, 0, 130));
                layout.getChildren().add(hbox);
                deleteGroup(Affichage_GR_chat().get(j),deleteIconGroup);

            }

        }
        for (int j=0;j<Affichage_private_chat().size();j++)
        { int idp =Affichage_private_chat().get(j).getId();
            nom.add(Affichage_private_chat().get(j));
            if(Affichage_private_chat().get(j).getReceived().getId()==4)
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getSender().getUsername());
                layout.getChildren().add(nomP );
                Line l =new Line();

                String nompr=Affichage_private_chat().get(j).getSender().getUsername();
                nomP.setOnMouseClicked(event->{
                    ImgSendMsg.setOnMouseClicked(event2->{

                        SendMsg(idp);
                    });
                        GetMesgsbyConv(idp);
                        conv.setVisible(true);
                        NomConv.setText(nompr);
                            Peoples.setVisible(false);
                        });
            }
            else
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getReceived().getUsername());
                System.out.println(Affichage_private_chat().get(j).getSender().getUsername());
                layout.getChildren().add(nomP);
                Line l =new Line();
                int idp2 =Affichage_private_chat().get(j).getId();

                String nompr=Affichage_private_chat().get(j).getReceived().getUsername();
                nomP.setOnMouseClicked(event->{
                    ImgSendMsg.setOnMouseClicked(event2->{

                        SendMsg(idp);
                    });
                    GetMesgsbyConv(idp2);
                    conv.setVisible(true);
                    NomConv.setText(nompr);
                    Peoples.setVisible(false);
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
               SenderLaber.setPadding(new Insets(1, 80, 0, 0));
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
               SenderLaber.setPadding(new Insets(1, 0, 0, 90));
                ContenuLabel.setPadding(new Insets(0, 0, 0, 80));
                DateLaber.setPadding(new Insets(0, 0, 1, 30));
                //SeparatorLabel.setPadding(new Insets(0, 0, 0, 80));
                //layout.setStyle("-fx-border-color:black");

                 FontAwesomeIconView deleteIconMsg = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                 HBox hbox=new HBox(deleteIconMsg);
                hbox.setStyle("-fx-alignement:right;");
                hbox.setMargin(deleteIconMsg,new Insets(0, 0, 0, 130));
                layout.getChildren().add(hbox);
               deletMsg(m,deleteIconMsg);

            }
        }
       s.setContent(layout);

    }

    @FXML
    public void addGroup(Event event) throws IOException {
        addGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = new Stage();
                Parent root = null;
               // GrClicked = gr;
                try {
                    root = FXMLLoader.load(getClass().getResource("fxml/AddGroup.fxml"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                primaryStage.setScene(new Scene( root));
                primaryStage.show();
            }
        });
    }
    public  void  SendMsg(int id)
    {

        MessageService MsgService =new MessageService();
         Conversation c= MsgService.getConversationById(id);
         //Current User
         User u = MsgService.getUserById(4);
            if(TfieldMessage.getText().isEmpty())
            {
                MsgErrorInputMsg.setVisible(true);
            }else
                {

                    MsgErrorInputMsg.setVisible(false);
                    Message msg = new Message(TfieldMessage.getText(),c,u);
                    MsgService.SendMsg(msg,c);
                    TfieldMessage.setText("");
                    GetMesgsbyConv(id);
                }
    }
    public void s(int id)
    {
        ImgSendMsg.setOnMouseClicked(event->{
            System.out.println("yyy"+id);
            SendMsg(id);});
    }

    public void deleteGroup(Conversation conversation,FontAwesomeIconView icon)
    {
        GroupeChatService GrService= new GroupeChatService();

        icon.setOnMouseClicked(event->{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure to delete Group "+conversation.getNom()+" ?");
            Optional<ButtonType> action =alert.showAndWait();
            if(action.get()== ButtonType.OK)
            {
                GrService.deleteGroup(conversation);
                ListConversation(event);
            }

        });
    }

    public void deletMsg(Message m,FontAwesomeIconView icon)
    {
        MessageService Servicemsg=new MessageService();
       // imgDeletmsg.setVisible(true);
        icon.setOnMouseClicked(event->{
            Servicemsg.deleteMessage(m);
            GetMesgsbyConv(m.getConversation().getId());
        });
    }



}
