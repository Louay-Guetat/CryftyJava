package edu.esprit.cryfty.gui;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.service.Nft.NftService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

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
    private Pane pnlPanier;

    @FXML
    private Button btnPanier;
    @FXML
    private Pane pnlTransactions;
    @FXML
    private Button btnTransactions;


    @FXML
    private Button btnAddNft;
    @FXML
    private ScrollPane scrollPaneItems;
    @FXML
    private HBox boxItems;

    public static Nft nft= null;
    public static Nft nftClicked= null;
    @FXML
    private Pane pnlItem;
    @FXML
    private AnchorPane view;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createView();

        ImageView add = new ImageView();
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\Siam Info\\Desktop\\JavaFinal\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\add.png");
            Image image = new Image(inputstream);
            add.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        btnAddNft.setGraphic(add);


    }
    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
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
            Node node = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
            view.getChildren().clear();
            view.getChildren().add(node);
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
        if(actionEvent.getSource()==btnPanier )
        {
            pnlPanier.setStyle("-fx-background-color : #02030A");
            pnlPanier.toFront();
            Node n = FXMLLoader.load(getClass().getResource("fxml/Cart.fxml"));
            pnlPanier.getChildren().add(n);
        }
        if(actionEvent.getSource()==btnTransactions )
        {
            pnlTransactions.setStyle("-fx-background-color : #02030A");
            pnlTransactions.toFront();
            Node n1 = FXMLLoader.load(getClass().getResource("fxml/Affichetransaction.fxml"));
            pnlTransactions.getChildren().add(n1);
        }
        if(actionEvent.getSource()==btnAddNft){
            Scene scene = btnAddNft.getScene();
            scene.getWindow().hide();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/AddNft.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

    }
    public void createView(){
        NftService nftService = new NftService();
        List<Nft> nfts = nftService.showNfts();
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
                        pnlItem.getChildren().clear();
                        pnlItem.getChildren().add(node);
                    }
                });
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
