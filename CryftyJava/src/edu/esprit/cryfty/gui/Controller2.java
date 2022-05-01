package edu.esprit.cryfty.gui;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
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
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class Controller2 extends Thread implements Initializable {

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
    boolean boolEmoji =true;
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

    private VBox layout;
    @FXML
    private ImageView emoji;
    @FXML
    private ScrollPane ScrollPaneEmoji;
    @FXML
    private Pane paneEmoji;
     String fromLang = "en";
    @FXML
    private ImageView imageBouleDiscussion;
    private FileChooser fileChooser;
    private File filePath;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectSocket();

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

    public Controller2() {
    }






    public void connectSocket() {
        try {
            socket = new Socket("localhost", 4444);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        GroupeChatService GrService= new GroupeChatService();
       User u= GrService.getUserById(3);
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println("token de 0 "+cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println("ful msg "+fulmsg);
                if (cmd.equalsIgnoreCase(u.getUsername() + "\n")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        if(!(u.getUsername().equals(cmd))) {
                            Label userEnvoyé = new Label(cmd + "");
                            userEnvoyé.setStyle("-fx-font-weight:bold;-fx-text-fill:Black");
                            layout.getChildren().add(userEnvoyé);

                            Label newMsg = new Label("  "+fulmsg + "  ");
                            newMsg.setStyle("-fx-text-fill: black;-fx-background-radius : 2em;-fx-background-color:#DCDCDC;");
                            layout.getChildren().add(newMsg);
                            String m = String.valueOf(fulmsg);
                            langues(layout, m);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.sql.Date dates = new java.sql.Date(System.currentTimeMillis());
                            String formats = formatter.format(dates);
                            Label labelDate = new Label(formats);
                            layout.getChildren().add(labelDate);
                            s.setContent(layout);
                            //afficheNotification();

                        }

                    }
                });



            }

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
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



    // TODO: If you have your own Premium account credentials, put them down here:
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public static void translate(String fromLang, String toLang, String text,Label Output2) throws Exception {
        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            Output2.setText(output);
        }
        conn.disconnect();
    }
    public void getEmoji(){
        VBox emojis = new VBox();

        for (Emoji emoji : EmojiManager.getAll()) {

            Label lEmoji = new Label(EmojiParser.parseToUnicode(emoji.getUnicode()));
           // lEmoji.setStyle("-fx-text-fill:black;");
            emojis.getChildren().add(lEmoji);
            System.out.println(EmojiParser.parseToUnicode(emoji.getUnicode()));
            lEmoji.setOnMouseClicked(event2->{
                String e =lEmoji.getText();
                TfieldMessage.setText(TfieldMessage.getText()+e);
            });
        }

        ScrollPaneEmoji.setContent(emojis);
    }
    public void affichListEmoji(Event actionEvent)
    {
        if(actionEvent.getSource() == emoji && boolEmoji == true ){

            paneEmoji.setVisible(true);
            boolEmoji=false;
            getEmoji();

        }else if(actionEvent.getSource() == emoji && boolEmoji == false)
        {
            paneEmoji.setVisible(false);
            boolEmoji=true;
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
            nomG.setStyle("-fx-font-weight:bold;");
            layout.getChildren().add(nomG);
          GroupeChat  gr=Affichage_GR_chat().get(j);
            int id =Affichage_GR_chat().get(j).getId();
            //System.out.println("idconv"+GrClicked2.getId());
            String nomgr=Affichage_GR_chat().get(j).getNom();
            Conversation g=Affichage_GR_chat().get(j);
            nomG.setOnMouseClicked(event->{
                System.out.println("id: "+id);
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
            if(Affichage_GR_chat().get(j).getOwner().getId()==3)
            {
                FontAwesomeIconView deleteIconGroup = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteIconGroup.setStyle("-fx-fill:red;");
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
            if(Affichage_private_chat().get(j).getReceived().getId()==3)
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getSender().getUsername());
                nomP.setStyle("-fx-font-weight:bold;");
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
                nomP.setStyle("-fx-font-weight:bold;");
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
    User u=   prvService.getUserById(3);
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
                if(Participants.get(j).getId()==3)
                {
                    nomGroup.add(Groups.get(i));
                }
            }
            if(Groups.get(i).getOwner().getId()==3)
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
        layout = new  VBox();
        layout.setStyle("-fx-pref-width:145;");

        for(int i=0;i<msgs.size();i++) {
            Message m = msgs.get(i);
            if (m.getSender().getId() != 3) {
                               HBox hbox=new HBox();
                Label SenderLaber = new Label(m.getSender().getUsername());
                layout.getChildren().add(SenderLaber);
                SenderLaber.setStyle("-fx-font-weight:bold;-fx-text-fill:Black");
                Label ContenuLabel = new Label("  "+m.getContenu()+"  ");

                ContenuLabel.setStyle("-fx-text-fill: black;-fx-background-radius : 2em;-fx-background-color:#DCDCDC;");
                layout.getChildren().add(ContenuLabel);

                langues(layout,m.getContenu());


                Label DateLaber = new Label(m.getCreatedAt());
                DateLaber.setStyle("-fx-font-family:'Robotom Medium'; ");
                layout.getChildren().add(DateLaber);

                Label SeparatorLabel = new Label("\n");
               layout.getChildren().add(SeparatorLabel);
               SenderLaber.setPadding(new Insets(1, 80, 0, 0));
               //ContenuLabel.setPadding(new Insets(1, 80, 0, 0));
                //ContenuLabel.setAlignment(Pos.BASELINE_RIGHT);
                DateLaber.setAlignment(Pos.BASELINE_RIGHT);
                SenderLaber.setAlignment(Pos.BASELINE_RIGHT);


            }else if(m.getSender().getId() == 3)
            {

                Label SenderLaber = new Label("you");
                SenderLaber.setStyle("-fx-font-weight:bold;-fx-text-fill:Black");
                layout.getChildren().add(SenderLaber);

                Label ContenuLabel = new Label("  "+m.getContenu()+"  ");
               ContenuLabel.setStyle("-fx-text-fill: black;-fx-background-radius : 2em;-fx-background-color:blue;");
                FontAwesomeIconView deleteIconMsg = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteIconMsg.setStyle("-fx-fill:#8B0000	;");
                HBox hbox=new HBox(ContenuLabel,deleteIconMsg);
              /*  hbox.setPrefWidth(5);
                hbox.setStyle("-fx-border-radius : 2em;-fx-background-color:blue;");*/
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                layout.getChildren().add(hbox);
                Label DateLaber = new Label(m.getCreatedAt());
                DateLaber.setStyle("-fx-font-family:'Robotom Medium'; ");
                layout.getChildren().add(DateLaber);
                SenderLaber.setPadding(new Insets(1, 0, 0, 100));
                //ContenuLabel.setPadding(new Insets(0, 0, 0, 50));
                DateLaber.setPadding(new Insets(0, 0, 1, 30));

               deletMsg(m,deleteIconMsg);

            }
        }
       s.setContent(layout);

    }

    @FXML
    public void addGroup() throws IOException {
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

         TfieldMessage.setStyle("-fx-text-fill:light;");
         //Current User
         User u = MsgService.getUserById(3);
            if(TfieldMessage.getText().isEmpty())
            {
                MsgErrorInputMsg.setVisible(true);
            }else
                {
                    String msg2 = TfieldMessage.getText();
                    writer.println(u.getUsername() + " " + msg2);
                    Label newMsg = new Label("me"+msg2 + "\n");
                    afficheNotification(u.getUsername());

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

public void langues (VBox vbox,String ContenuMsg)
{
       HashMap<String ,String >  l=new HashMap<>();
       ComboBox langue = new ComboBox();
         l.put("Afrikaans","af");
         l.put( "Arabic" ,"ar");
         l.put(  "Azerbaijani","az");
        l.put( "Belarusian","be");
        l.put(  "Bulgarian","bg");
        l.put( "Bengali","bn" );
        l.put( "Bosnian","bs" );
        l.put( "Catalan","ca");
        l.put( "Cebuano","ceb");
        l.put( "Czech","cs");
        l.put("Welsh","cy" );
        l.put( "Danish","da");
        l.put("German", "de");
        l.put( "Greek","el");
        l.put("English","en" );
        l.put("Esperanto","eo" );
        l.put("Spanish","es");
        l.put( "Estonian","et");
        l.put("Basque","eu" );
        l.put( "Persian","fa");
        l.put( "Finnish","fi");
        l.put( "French","fr");
        l.put("Irish","ga" );
        l.put(  "Galician","gl");
        l.put( "Gujarati","gu" );
        l.put( "Hausa", "ha");
        l.put( "Hindi","hi");
        l.put("Hmong","hmn" );
        l.put("Croatian","hr" );
        l.put( "Haitian Creole","ht");
        l.put("Hungarian","hu" );
        l.put("Armenian","hy" );
        l.put( "Indonesian","id");
        l.put("Igbo","ig" );
        l.put("Icelandic","is");
        l.put( "Italian","it");
        l.put("Hebrew","iw" );
        l.put( "Japanese","ja");
        l.put("Javanese","jw");
        l.put( "Georgian","ka");
        l.put( "Kazakh","kk");
        l.put("Khmer","km" );
        l.put("Kannada","kn" );
        l.put( "Korean","ko");
        l.put("Latin","la" );
        l.put("Lao","lo" );
        l.put( "Lithuanian","lt");
        l.put("Latvian","lv" );
        l.put(  "Punjabi","ma");
        l.put( "Malagasy","mg" );
        l.put( "Maori","mi");
        l.put("Macedonian","mk");
        l.put( "Malayalam","ml");
        l.put( "Mongolian","mn");
        l.put( "Marathi","mr");
        l.put( "Malay","ms");
        l.put( "Maltese","mt");
        l.put( "Myanmar (Burmese)","my");
        l.put( "Nepali","ne");
        l.put( "Dutch","nl");
        l.put( "Norwegian","no");
        l.put( "Chichewa","ny");
        l.put( "Polish", "pl");
        l.put("Portuguese","pt");
        l.put( "Romanian","ro");
        l.put( "Russian" ,"ru");
        l.put("Sinhala" ,"si" );
        l.put( "Slovak","sk" );
        l.put( "Slovenian", "sl");
        l.put( "Somali","so");
        l.put( "Albanian", "sq");
        l.put("Serbian","sr");
        l.put( "Sesotho","st");
        l.put( "Sudanese","su");
        l.put( "Swedish","sv");
        l.put("Swahili","sw");
        l.put( "Tamil","ta");
        l.put( "Telugu","te");
        l.put(  "Tajik","tg");
        l.put("Thai","th" );
        l.put( "Filipino","tl");
        l.put("Turkish","tr" );
        l.put("Ukrainian","uk");
        l.put( "Urdu", "ur");
        l.put("Uzbek","uz");
        l.put("Vietnamese","vi");
        l.put( "Yiddish","yi");
        l.put( "Yoruba","yo");
        l.put("Chinese Simplified","zh-CN");
        l.put( "Chinese Traditional","zh-TW");
        l.put("Zulu","zu");
 ArrayList<String> k= new ArrayList<>();
    l.entrySet().stream().forEach(e -> {
       k.add(e.getKey());
    });
    ObservableList<String> listlang= FXCollections.observableArrayList(k);
    langue.setItems(listlang);
    vbox.getChildren().add(langue);
          langue.setStyle("-fx-background-color:#BDB76B;-fx-font-weight:bold;-fx-text-fill:black");
          langue.setPrefWidth(80);

     FontAwesomeIconView traductionIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCHANGE);
   traductionIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            String langSelected =langue.getSelectionModel().getSelectedItem().toString();
            System.out.println("lang selecte"+langSelected);
            String ToLangue=l.get(langSelected);
            System.out.println("value de key"+ToLangue);
            Label tr = new Label();
            tr.setStyle("-fx-font-weight:bold;-fx-text-fill: purple");
            vbox.getChildren().add(tr);
            try {
                Controller.translate(fromLang,ToLangue, ContenuMsg,tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    vbox.getChildren().add(traductionIcon);
}
    @Deprecated
    public void afficheNotification(String user) {
        ImageView img = new ImageView("file:C:\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\newMessage.png");
       img.setFitHeight(65);
       img.setFitWidth(80);
        Notifications notificationBuilder = Notifications.create()
                .title("Cryfty")
                .text("You have a new message from "+user)
                .graphic((img))
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT)
                ;
        notificationBuilder.darkStyle();
              notificationBuilder.show();
    }
}
