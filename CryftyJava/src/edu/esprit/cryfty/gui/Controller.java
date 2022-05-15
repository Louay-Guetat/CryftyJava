package edu.esprit.cryfty.gui;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.chat.Conversation;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.entity.chat.Message;
import edu.esprit.cryfty.entity.chat.PrivateChat;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.service.chat.GroupeChatService;
import edu.esprit.cryfty.service.chat.MessageService;
import edu.esprit.cryfty.service.chat.PrivateChatService;
import edu.esprit.cryfty.service.user.Session;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.service.blogs.BlogsService;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Main.stage;
import static edu.esprit.cryfty.gui.fxml.ExploreController.nft1;

public class Controller extends Thread implements Initializable {

    @FXML
    private Button btnMenus;

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
    private Pane pnlArticles;

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
    @FXML
    private ScrollPane scrollPaneItems;
    @FXML
    private AnchorPane view;
    @FXML
    private Button btnAddNft;
    @FXML
    private Pane pnlItem;
    @FXML
    private HBox boxItems;

    public static Nft nftClicked;
    public static Nft nft;
    @FXML
    private Button btnPanier;
    @FXML
    private Pane pnlPanier;
    @FXML
    private Button btnTransactions;
    @FXML
    private Pane pnlTransactions;
    @FXML
    private StackPane home;
    @FXML
    private Pane pnlHome;
    @FXML
    private Button btnExplore;
    @FXML
    private Button btnWallets;
    @FXML
    private Label lblUsername;
    @FXML
    private Button btnReclamations;
    @FXML
    private Button btnOffice;
    @FXML
    private Button btnNodeOffice;
    @FXML
    private VBox vboxOffice;
    @FXML
    private Button btnTransactionsOffice;
    @FXML
    private Button btnReclamationsOffice;
    @FXML
    public Button btnBlogsOffice;
    @FXML
    private Button btnCategoriesOffice;
    @FXML
    private VBox vboxFront;
    @FXML
    private Button btnBackToFront;
    @FXML
    private Label lblUsername1;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfil;

    private FileChooser fileChooser;
    private File filePath;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    private List<Nft> nfts;
    private boolean bool = true;
    boolean boolEmoji = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view.setPrefHeight(750);
        view.setPrefWidth(1500);
        System.out.println(Session.getInstance().getCurrentUser());
        if(Session.getInstance().getCurrentUser().getRoles().equals("[\"ROLE_CLIENT\"]")){
            btnOffice.setVisible(false);
        }
        else{
            btnOffice.setVisible(true);
        }
        lblUsername.setText(Session.getInstance().getCurrentUser().getUsername());
        lblUsername1.setText(Session.getInstance().getCurrentUser().getUsername());
        boxItems.setPrefWidth(1196);
        boxItems.setPrefHeight(365);
        scrollPaneItems.setContent(boxItems);
        scrollPaneItems.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pnlItem.setLayoutY(150);
        ImageView add = new ImageView();
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\add.png");
            Image image = new Image(inputstream);
            add.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        btnAddNft.setGraphic(add);

        connectSocket();
        Node[] nodes = new Node[10];
        ArrayList<BlogArticles> list = (ArrayList<BlogArticles>) BlogsService.listerArticles();
        int n;
        n = list.size();

        conv.toFront();
        paneEmoji.toFront();
        ListConversation.toFront();
        imageBouleDiscussion.toFront();
        createView();
    }

    public Controller() {
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
        GroupeChatService GrService = new GroupeChatService();
        User u = GrService.getUserById(Session.getInstance().getCurrentUser().getId());
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(u.getUsername() + "\n")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!(u.getUsername().equals(cmd))) {
                            Label userEnvoyé = new Label(cmd + "");
                            afficheNotification(cmd);
                            userEnvoyé.setStyle("-fx-text-fill: black;-fx-font-weight:bold;-fx-text-fill:Black");
                            layout.getChildren().add(userEnvoyé);

                            Label newMsg = new Label("  " + fulmsg + "  ");
                            newMsg.setStyle("-fx-background-radius : 2em;-fx-background-color:#DCDCDC;");
                            layout.getChildren().add(newMsg);
                            String m = String.valueOf(fulmsg);
                            langues(layout, m);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.sql.Date dates = new java.sql.Date(System.currentTimeMillis());
                            String formats = formatter.format(dates);
                            Label labelDate = new Label(formats);
                            layout.getChildren().add(labelDate);
                            s.setContent(layout);

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

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource()==btnAddNft){
            pnlHome.getChildren().clear();
            Node node = new FXMLLoader().load(getClass().getResource("fxml/AddNft.fxml"));
            node.setLayoutX(80);
            node.setLayoutY(20);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnWallets) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/Wallets.fxml"));
            node.setLayoutX(120);
            node.setLayoutY(50);
            pnlHome.getChildren().add(node);
        }

        if(actionEvent.getSource() == btnReclamations){
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/Addreclamation.fxml"));
            node.setLayoutX(120);
            node.setLayoutY(50);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnPanier) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/Cart.fxml"));
            node.setLayoutX(80);
            node.setLayoutY(80);
            pnlHome.getChildren().add(node);
        }
        if (actionEvent.getSource() == btnTransactions) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/Affichetransaction.fxml"));
            node.setLayoutX(120);
            node.setLayoutY(80);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnMenus) {
            pnlHome.getChildren().clear();

            Node node = FXMLLoader.load(getClass().getResource("fxml/ListeArticles.fxml"));
            node.setLayoutX(120);
            node.setLayoutY(80);
            pnlHome.getChildren().add(node);

        }

        if (actionEvent.getSource() == btnHome) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
            pnlHome.getChildren().clear();
            pnlHome.getChildren().add(pnlOverview);
        }

        if (actionEvent.getSource() == btnExplore) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/Explore.fxml"));
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnOffice) {
            vboxOffice.setVisible(true);
            vboxFront.setVisible(false);
            pnlHome.getChildren().clear();
            Button btnStats = new Button("Check Stats");
            pnlHome.getChildren().add(btnStats);
            btnStats.setLayoutX(930);
            btnStats.setLayoutY(150);
            btnStats.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pnlHome.getChildren().clear();
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("fxml/Gchart.fxml"));
                        System.out.println("done");
                        pnlHome.getChildren().add(node);
                        node.setLayoutY(120);
                        node.setLayoutX(80);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            initiateCategories();
            initiateSubCategories();
            Button btnCategory = new Button("Add");
            pnlHome.getChildren().add(btnCategory);
            btnCategory.setLayoutY(150);
            btnCategory.setLayoutX(45);
            btnCategory.setPrefWidth(100);

            btnCategory.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("fxml/AddCategory.fxml"));
                        pnlHome.getChildren().clear();
                        pnlHome.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (actionEvent.getSource() == btnCategoriesOffice) {
            pnlHome.getChildren().clear();
            Button btnStats = new Button("Check Stats");
            pnlHome.getChildren().add(btnStats);
            btnStats.setLayoutX(930);
            btnStats.setLayoutY(150);
            btnStats.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pnlHome.getChildren().clear();
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("fxml/Gchart.fxml"));
                        System.out.println("done");
                        pnlHome.getChildren().add(node);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            initiateCategories();
            initiateSubCategories();
            Button btnCategory = new Button("Add");
            pnlHome.getChildren().add(btnCategory);
            btnCategory.setLayoutY(150);
            btnCategory.setLayoutX(45);
            btnCategory.setPrefWidth(100);

            btnCategory.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("fxml/AddCategory.fxml"));
                        pnlHome.getChildren().clear();
                        pnlHome.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (actionEvent.getSource() == btnBlogsOffice) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/boarticle.fxml"));
            node.setLayoutX(160);
            node.setLayoutY(150);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnProfil) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/UpdateClient.fxml"));
            node.setLayoutX(160);
            node.setLayoutY(150);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnNodeOffice) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/node/NodeCrud.fxml"));
            node.setLayoutX(200);
            node.setLayoutY(170);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnReclamationsOffice) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/reclamation.fxml"));
            node.setLayoutX(120);
            node.setLayoutY(80);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnTransactionsOffice) {
            pnlHome.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource("fxml/afficheTransactionBO.fxml"));
            node.setLayoutX(135);
            node.setLayoutY(100);
            pnlHome.getChildren().add(node);
        }

        if (actionEvent.getSource() == btnBackToFront) {
            vboxOffice.setVisible(false);
            vboxFront.setVisible(true);
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
            pnlHome.getChildren().clear();
            pnlHome.getChildren().add(pnlOverview);
        }

        if(actionEvent.getSource() == btnSignout){
            Session.getInstance().setCurrentUser(null);
            stage.hide();
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
            stage.setScene(new Scene(tableViewParent));
            stage.show();
        }
    }

    @FXML
    public void afficheListConversation(Event actionEvent) {

        if (actionEvent.getSource() == boule  && bool == true) {
            ListConversation.setVisible(true);
            bool = false;
            ListConversation(actionEvent);

        } else if (actionEvent.getSource() == boule && bool == false) {
            ListConversation.setVisible(false);
            bool = true;
            conv.setVisible(false);
        }

        if (actionEvent.getSource() == imageBouleDiscussion  && bool == true) {
            ListConversation.setVisible(true);
            bool = false;
            ListConversation(actionEvent);

        } else if (actionEvent.getSource() == imageBouleDiscussion && bool == false) {
            ListConversation.setVisible(false);
            bool = true;
            conv.setVisible(false);
        }
    }

    public void articlebo() {
        BorderPane root = new BorderPane();
        TableView<BlogArticles> table = new TableView<BlogArticles>();

        TableColumn<BlogArticles, String> title = new TableColumn<BlogArticles, String>("Title");
        title.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("title"));

        TableColumn<BlogArticles, String> category = new TableColumn<BlogArticles, String>("Category");
        category.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("category"));

        TableColumn<BlogArticles, Date> date = new TableColumn<BlogArticles, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<BlogArticles, Date>("date"));

        TableColumn<BlogArticles, String> contents = new TableColumn<BlogArticles, String>("Contents");
        contents.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("contents"));
        TableColumn<BlogArticles, String> author = new TableColumn<BlogArticles, String>("Author");
        author.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("author"));

        table.getColumns().add(title);
        table.getColumns().add(contents);
        table.getColumns().add(category);
        table.getColumns().add(date);

        table.getColumns().add(author);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        BlogsService Service = new BlogsService();
        List<BlogArticles> articles = BlogsService.listerArticles();
        for (int i = 0; i < articles.size(); i++) {
            table.getItems().add(articles.get(i));
        }
        Stage primaryStage = new Stage();
        root.setCenter(table);
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("TableView Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // TODO: If you have your own Premium account credentials, put them down here:
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public static void translate(String fromLang, String toLang, String text, Label Output2) throws Exception {
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

    public void getEmoji() {
        VBox emojis = new VBox();

        for (Emoji emoji : EmojiManager.getAll()) {

            Label lEmoji = new Label(EmojiParser.parseToUnicode(emoji.getUnicode()));
            // lEmoji.setStyle("-fx-text-fill:black;");
            emojis.getChildren().add(lEmoji);
            System.out.println(EmojiParser.parseToUnicode(emoji.getUnicode()));
            lEmoji.setOnMouseClicked(event2 -> {
                String e = lEmoji.getText();
                TfieldMessage.setText(TfieldMessage.getText() + e);
            });
        }

        ScrollPaneEmoji.setContent(emojis);
    }

    @FXML
    public void affichListEmoji(Event actionEvent) {
        if (actionEvent.getSource() == emoji && boolEmoji == true) {

            paneEmoji.setVisible(true);
            boolEmoji = false;
            getEmoji();

        } else if (actionEvent.getSource() == emoji && boolEmoji == false) {
            paneEmoji.setVisible(false);
            boolEmoji = true;
        }
    }

    FontAwesomeIconView Peoples = new FontAwesomeIconView(FontAwesomeIcon.USERS);
    public static GroupeChat GrClicked = null;

    public void ListConversation (Event actionEvent)
    {
        ArrayList<Conversation> nom = new ArrayList<>();
        VBox layout = new  VBox(nom.size());
        MessageService msgS = new MessageService();
        for (int j=0;j< Affichage_GR_chat().size();j++)
        {
            nom.add( Affichage_GR_chat().get(j));
            GroupeChat  gr=Affichage_GR_chat().get(j);
            Label nomG = new Label(Affichage_GR_chat().get(j).getNom());

            nomG.setStyle("-fx-font-weight:bold;");
            layout.getChildren().add(nomG);
            for(int z =0;z<msgS.getLastMessageByCon(gr).size();z++)
            {Label lastm = new Label(msgS.getLastMessageByCon(gr).get(z).getContenu()+" ");
                layout.getChildren().add(lastm);}
            Line l =new Line();
            layout.getChildren().add(l);
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
            if(Affichage_GR_chat().get(j).getOwner().getId()==4)
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
            if(Affichage_private_chat().get(j).getReceived().getId()==4)
            {
                Label nomP = new Label(Affichage_private_chat().get(j).getSender().getUsername());
                nomP.setStyle("-fx-font-weight:bold;");

                layout.getChildren().add(nomP );
                for(int z =0;z<msgS.getLastMessageByCon(Affichage_private_chat().get(j)).size();z++)
                {Label lastm = new Label(msgS.getLastMessageByCon(Affichage_private_chat().get(j)).get(z).getContenu()+" ");
                    layout.getChildren().add(lastm);}
                Line l =new Line();
                layout.getChildren().add(l);
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
                for(int z =0;z<msgS.getLastMessageByCon(Affichage_private_chat().get(j)).size();z++)
                {Label lastm = new Label(msgS.getLastMessageByCon(Affichage_private_chat().get(j)).get(z).getContenu()+" ");
                    layout.getChildren().add(lastm);}
                Line l =new Line();
                layout.getChildren().add(l);
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

    public ArrayList<PrivateChat> Affichage_private_chat() {
        ArrayList<PrivateChat> PrivateChat = new ArrayList<>();
        PrivateChatService prvService = new PrivateChatService();
        // user connecté
        User u = prvService.getUserById(Session.getInstance().getCurrentUser().getId());
        ArrayList<PrivateChat> privateChat = prvService.privateChat(u);
        for (int p = 0; p < privateChat.size(); p++) {
            PrivateChat.add(privateChat.get(p));
        }
        return PrivateChat;
    }

    public ArrayList<GroupeChat> Affichage_GR_chat() {
        GroupeChatService GrService = new GroupeChatService();
        ArrayList<GroupeChat> Groups = GrService.getGroups();
        ArrayList<GroupeChat> nomGroup = new ArrayList<>();
        for (int i = 0; i < Groups.size(); i++) {
            ArrayList<User> Participants = Groups.get(i).getParticipants();
            for (int j = 0; j < Participants.size(); j++) {
                if (Participants.get(j).getId() == Session.getInstance().getCurrentUser().getId()) {
                    nomGroup.add(Groups.get(i));
                }
            }
            if (Groups.get(i).getOwner().getId() == Session.getInstance().getCurrentUser().getId()) {
                nomGroup.add(Groups.get(i));
            }
        }
        return nomGroup;
    }

    @FXML
    public void closeConv(Event event) {
        conv.setVisible(false);
    }

    public void GetMesgsbyConv(int id) {
        MessageService msgService = new MessageService();
        ArrayList<Message> msgs = msgService.getMessageByCon(msgService.getConversationById(id));
        layout = new VBox();
        layout.setStyle("-fx-pref-width:145;");

        for (int i = 0; i < msgs.size(); i++) {
            Message m = msgs.get(i);
            if (m.getSender().getId() != Session.getInstance().getCurrentUser().getId()) {
                HBox hbox = new HBox();
                Label SenderLaber = new Label(m.getSender().getUsername());
                layout.getChildren().add(SenderLaber);
                SenderLaber.setStyle("-fx-font-weight:bold;-fx-text-fill:Black");
                Label ContenuLabel = new Label("  " + m.getContenu() + "  ");

                ContenuLabel.setStyle("-fx-text-fill: black;-fx-background-radius : 2em;-fx-background-color:#DCDCDC;");
                layout.getChildren().add(ContenuLabel);

                langues(layout, m.getContenu());


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


            } else if (m.getSender().getId() == Session.getInstance().getCurrentUser().getId()) {

                Label SenderLaber = new Label("you");
                SenderLaber.setStyle("-fx-font-weight:bold;-fx-text-fill:Black");
                layout.getChildren().add(SenderLaber);

                Label ContenuLabel = new Label("  " + m.getContenu() + "  ");
                ContenuLabel.setStyle("-fx-text-fill: black;-fx-background-radius : 2em;-fx-background-color:blue;");
                FontAwesomeIconView deleteIconMsg = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteIconMsg.setStyle("-fx-fill:#8B0000;");
                HBox hbox = new HBox(ContenuLabel, deleteIconMsg);
              /*  hbox.setPrefWidth(5);
                hbox.setStyle("-fx-border-radius : 2em;-fx-background-color:blue;");*/
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                layout.getChildren().add(hbox);
                Label DateLaber = new Label(m.getCreatedAt());
                DateLaber.setStyle("-fx-font-family:'Robotom Medium'; ");
                layout.getChildren().add(DateLaber);
                SenderLaber.setPadding(new Insets(1, 0, 0, 100));
                //ContenuLabel.setPadding(new Insets(0, 0, 0, 50));
                DateLaber.setPadding(new Insets(0, 0, 1, 2));

                deletMsg(m, deleteIconMsg);

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
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
        });
    }

    public void SendMsg(int id) {
        MessageService MsgService = new MessageService();
        Conversation c = MsgService.getConversationById(id);

        TfieldMessage.setStyle("-fx-text-fill:light;");
        //Current User
        User u = MsgService.getUserById(Session.getInstance().getCurrentUser().getId());
        if (TfieldMessage.getText().isEmpty()) {
            MsgErrorInputMsg.setVisible(true);
        } else {
            String msg2 = TfieldMessage.getText();
            writer.println(u.getUsername() + "  " + msg2);

            MsgErrorInputMsg.setVisible(false);
            Message msg = new Message(TfieldMessage.getText(), c, u);
            MsgService.SendMsg(msg, c);

            TfieldMessage.setText("");
            GetMesgsbyConv(id);
        }
    }

    public void s(int id) {
        ImgSendMsg.setOnMouseClicked(event -> {
            System.out.println("yyy" + id);
            SendMsg(id);
        });
    }

    public void deleteGroup(Conversation conversation, FontAwesomeIconView icon) {
        GroupeChatService GrService = new GroupeChatService();

        icon.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure to delete Group " + conversation.getNom() + " ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                GrService.deleteGroup(conversation);
                ListConversation(event);
            }

        });
    }

    public void deletMsg(Message m, FontAwesomeIconView icon) {
        MessageService Servicemsg = new MessageService();
        // imgDeletmsg.setVisible(true);
        icon.setOnMouseClicked(event -> {
            Servicemsg.deleteMessage(m);
            GetMesgsbyConv(m.getConversation().getId());
        });
    }

    public void langues(VBox vbox, String ContenuMsg) {
        HashMap<String, String> l = new HashMap<>();
        ComboBox langue = new ComboBox();
        l.put("Afrikaans", "af");
        l.put("Arabic", "ar");
        l.put("Azerbaijani", "az");
        l.put("Belarusian", "be");
        l.put("Bulgarian", "bg");
        l.put("Bengali", "bn");
        l.put("Bosnian", "bs");
        l.put("Catalan", "ca");
        l.put("Cebuano", "ceb");
        l.put("Czech", "cs");
        l.put("Welsh", "cy");
        l.put("Danish", "da");
        l.put("German", "de");
        l.put("Greek", "el");
        l.put("English", "en");
        l.put("Esperanto", "eo");
        l.put("Spanish", "es");
        l.put("Estonian", "et");
        l.put("Basque", "eu");
        l.put("Persian", "fa");
        l.put("Finnish", "fi");
        l.put("French", "fr");
        l.put("Irish", "ga");
        l.put("Galician", "gl");
        l.put("Gujarati", "gu");
        l.put("Hausa", "ha");
        l.put("Hindi", "hi");
        l.put("Hmong", "hmn");
        l.put("Croatian", "hr");
        l.put("Haitian Creole", "ht");
        l.put("Hungarian", "hu");
        l.put("Armenian", "hy");
        l.put("Indonesian", "id");
        l.put("Igbo", "ig");
        l.put("Icelandic", "is");
        l.put("Italian", "it");
        l.put("Hebrew", "iw");
        l.put("Japanese", "ja");
        l.put("Javanese", "jw");
        l.put("Georgian", "ka");
        l.put("Kazakh", "kk");
        l.put("Khmer", "km");
        l.put("Kannada", "kn");
        l.put("Korean", "ko");
        l.put("Latin", "la");
        l.put("Lao", "lo");
        l.put("Lithuanian", "lt");
        l.put("Latvian", "lv");
        l.put("Punjabi", "ma");
        l.put("Malagasy", "mg");
        l.put("Maori", "mi");
        l.put("Macedonian", "mk");
        l.put("Malayalam", "ml");
        l.put("Mongolian", "mn");
        l.put("Marathi", "mr");
        l.put("Malay", "ms");
        l.put("Maltese", "mt");
        l.put("Myanmar (Burmese)", "my");
        l.put("Nepali", "ne");
        l.put("Dutch", "nl");
        l.put("Norwegian", "no");
        l.put("Chichewa", "ny");
        l.put("Polish", "pl");
        l.put("Portuguese", "pt");
        l.put("Romanian", "ro");
        l.put("Russian", "ru");
        l.put("Sinhala", "si");
        l.put("Slovak", "sk");
        l.put("Slovenian", "sl");
        l.put("Somali", "so");
        l.put("Albanian", "sq");
        l.put("Serbian", "sr");
        l.put("Sesotho", "st");
        l.put("Sudanese", "su");
        l.put("Swedish", "sv");
        l.put("Swahili", "sw");
        l.put("Tamil", "ta");
        l.put("Telugu", "te");
        l.put("Tajik", "tg");
        l.put("Thai", "th");
        l.put("Filipino", "tl");
        l.put("Turkish", "tr");
        l.put("Ukrainian", "uk");
        l.put("Urdu", "ur");
        l.put("Uzbek", "uz");
        l.put("Vietnamese", "vi");
        l.put("Yiddish", "yi");
        l.put("Yoruba", "yo");
        l.put("Chinese Simplified", "zh-CN");
        l.put("Chinese Traditional", "zh-TW");
        l.put("Zulu", "zu");
        ArrayList<String> k = new ArrayList<>();
        l.entrySet().stream().forEach(e -> {
            k.add(e.getKey());
        });
        ObservableList<String> listlang = FXCollections.observableArrayList(k);
        langue.setItems(listlang);
        vbox.getChildren().add(langue);
        langue.setStyle("-fx-background-color:#BDB76B;-fx-font-weight:bold;-fx-text-fill:black");
        langue.setPrefWidth(80);

        FontAwesomeIconView traductionIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCHANGE);
        traductionIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String langSelected = langue.getSelectionModel().getSelectedItem().toString();
                System.out.println("lang selecte" + langSelected);
                String ToLangue = l.get(langSelected);
                System.out.println("value de key" + ToLangue);
                Label tr = new Label();
                tr.setStyle("-fx-font-weight:bold;-fx-text-fill: purple");
                vbox.getChildren().add(tr);
                try {
                    Controller.translate(fromLang, ToLangue, ContenuMsg, tr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        vbox.getChildren().add(traductionIcon);

    }

    @Deprecated
    public void afficheNotification(String user) {
        ImageView img = new ImageView("file:C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\newMessage.png");
        img.setFitHeight(65);
        img.setFitWidth(80);
        Notifications notificationBuilder = Notifications.create()
                .title("Cryfty")
                .text("You have a new message from " + user)
                .graphic((img))
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public void createView() {
        nft1 = null;
        NftService nftService = new NftService();
        nfts = nftService.showNfts();
        Node[] nodes = new Node[nfts.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nft = nfts.get(i);
                nodes[i] = FXMLLoader.load(getClass().getResource("fxml/Item.fxml"));
                //give the items some effect
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                boxItems.getChildren().add(nodes[i]);

                int finalI = i;
                nodes[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        nftClicked = nfts.get(finalI);
                        Node node = nodes[finalI];
                        try {
                            node = FXMLLoader.load(getClass().getResource("fxml/OneItem.fxml"));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        pnlHome.getChildren().clear();
                        pnlHome.getChildren().add(node);
                        node.setLayoutX(50);
                        node.setLayoutY(80);
                    }
                });
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void initiateCategories() throws IOException {
        TableView<Category> tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.setStyle("-fx-background-color: transparent;");
        TableColumn<Category, String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        name.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category, LocalDateTime> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        creationDate.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category, String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        nbrNfts.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category, String> nbrSubCat = new TableColumn("Number of subCategories");
        nbrSubCat.setPrefWidth(150);
        nbrSubCat.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category, Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(200);
        delete.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-alignment: CENTER;");
        delete.setResizable(false);

        tableView.getColumns().addAll(name, creationDate, nbrNfts, nbrSubCat, delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<Category>forTableColumn());
        name.setOnEditCommit((TableColumn.CellEditEvent<Category, String> event) -> {
            TablePosition<Category, String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            Category category = event.getTableView().getItems().get(row);
            if (!newName.isEmpty()) {
                CategoryService categoryService = new CategoryService();
                category.setName(newName);
                categoryService.updateCategory(category);
            }
        });

        creationDate.setCellFactory(column -> {
            TableCell<Category, LocalDateTime> cell = new TableCell<Category, LocalDateTime>() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        item = getTableView().getItems().get(getIndex()).getCreationDate();
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        nbrNfts.setCellValueFactory(new PropertyValueFactory<>("nbrNfts"));
        nbrSubCat.setCellValueFactory(new PropertyValueFactory<>("nbrSubCategories"));

        Callback<TableColumn<Category, Void>, TableCell<Category, Void>> cellDelete = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Category category = getTableView().getItems().get(getIndex());
                            if (category.getNbrSubCategories() == 0 && category.getNbrNfts() == 0) {
                                CategoryService categoryService = new CategoryService();
                                categoryService.deleteCategory(category);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Error");
                                alert.setHeaderText("You can't delete this Category because it contains Products/SubCategories");
                                alert.showAndWait();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        delete.setCellFactory(cellDelete);

        ObservableList<Category> list = FXCollections.observableArrayList();
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.showCategories();
        for (int i = 0; i < categories.size(); i++) {
            list.add(categories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setMaxSize(1020, 180);
        tableView.setFixedCellSize(40);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlHome.getChildren().add(tableView);
        tableView.setLayoutY(230);
        tableView.setLayoutX(30);
    }

    public void initiateSubCategories() {
        TableView<SubCategory> tableView = new TableView<SubCategory>();
        tableView.setEditable(true);
        tableView.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        TableColumn<SubCategory, String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        name.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-selection-bar: red;");
        TableColumn<SubCategory, LocalDateTime> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        creationDate.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<SubCategory, String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        nbrNfts.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<SubCategory, String> category = new TableColumn<SubCategory, String>("Category");
        category.setPrefWidth(150);
        category.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");

        TableColumn<SubCategory, Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(200);
        delete.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-alignment: CENTER;");
        delete.setResizable(false);

        tableView.getColumns().addAll(name, creationDate, nbrNfts, category, delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<SubCategory>forTableColumn());

        name.setOnEditCommit((TableColumn.CellEditEvent<SubCategory, String> event) -> {
            TablePosition<SubCategory, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            SubCategory subCategory = event.getTableView().getItems().get(row);
            if (!newName.isEmpty()) {
                SubCategoryService subCategoryService = new SubCategoryService();
                subCategory.setName(newName);
                subCategoryService.updateSubCategory(subCategory);
            }
        });

        creationDate.setCellFactory(column -> {
            TableCell<SubCategory, LocalDateTime> cell = new TableCell<SubCategory, LocalDateTime>() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        item = getTableView().getItems().get(getIndex()).getCreationDate();
                        setText(item.format(formatter));
                    }
                }
            };
            return cell;
        });
        nbrNfts.setCellValueFactory(new PropertyValueFactory<>("nbrNfts"));

        CategoryService categoryService = new CategoryService();
        List<Category> categoryList = categoryService.showCategories();
        String[] categoryNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            categoryNames[i] = categoryList.get(i).getName();
        }

        ObservableList<String> categories = FXCollections.observableArrayList(categoryNames);
        category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategory, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategory, String> param) {
                SubCategory subCategory = param.getValue();
                String categoryName = subCategory.getCategory().getName();
                Category cat = categoryService.findCategoryByName(categoryName);
                return new SimpleObjectProperty<String>(cat.getName());
            }
        });
        category.setCellFactory(ComboBoxTableCell.forTableColumn(categories));

        category.setOnEditCommit((TableColumn.CellEditEvent<SubCategory, String> event) -> {
            TablePosition<SubCategory, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            SubCategory subCategory = event.getTableView().getItems().get(row);
            if (!newName.isEmpty()) {
                SubCategoryService subCategoryService = new SubCategoryService();
                Category cat = categoryService.findCategoryByName(newName);
                subCategory.setCategory(cat);
                subCategoryService.updateSubCategory(subCategory);
            }
        });

        Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>> cellDelete = new Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>>() {
            @Override
            public TableCell<SubCategory, Void> call(final TableColumn<SubCategory, Void> param) {
                final TableCell<SubCategory, Void> cell = new TableCell<SubCategory, Void>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            SubCategory subCategory = getTableView().getItems().get(getIndex());
                            if (subCategory.getNbrNfts() == 0) {
                                SubCategoryService subCategoryService = new SubCategoryService();
                                subCategoryService.deleteSubCategory(subCategory);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Error");
                                alert.setHeaderText("You can't delete this SubCategory because it contains Products");
                                alert.showAndWait();
                            }
                            //deleteHere

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        delete.setCellFactory(cellDelete);

        ObservableList<SubCategory> list = FXCollections.observableArrayList();
        SubCategoryService subCategoryService = new SubCategoryService();
        List<SubCategory> subCategories = subCategoryService.showSubCategories();
        for (int i = 0; i < subCategories.size(); i++) {
            list.add(subCategories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setMaxSize(1020, 180);
        tableView.setFixedCellSize(40);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlHome.getChildren().add(tableView);
        tableView.setLayoutY(400);
        tableView.setLayoutX(30);
    }

    public void setPane(String path) throws IOException {
        pnlHome.getChildren().clear();
        Node node = FXMLLoader.load(getClass().getResource("fxml/"+path));
        pnlHome.getChildren().add(node);
    }

}

