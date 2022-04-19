package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.gui.fxml.ItemController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Pane pnlOrders;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Button btnCustomers;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Button btnOverview;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnMenus;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Button btnAddNft;
    @FXML
    private ScrollPane scrollPaneItems;
    @FXML
    private HBox boxItems;

    public static Nft nft= null;
    public static Nft nftClicked= null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NftService nftService = new NftService();
        List<Nft> nfts = nftService.showNfts();
        Node[] nodes = new Node[nfts.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nft = nfts.get(i);
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
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
                        Stage primaryStage = new Stage();
                        Parent root = null;
                        nftClicked = nfts.get(finalI);
                        try {
                            root = FXMLLoader.load(getClass().getResource("OneItem.fxml"));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
            pnlOrders.setVisible(false);
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
            pnlCustomer.setVisible(false);
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
            pnlOrders.setVisible(true);
        }
        if(actionEvent.getSource()==btnAddNft){
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddNft.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

}
