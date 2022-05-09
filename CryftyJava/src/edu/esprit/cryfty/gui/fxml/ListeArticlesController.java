package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class ListeArticlesController implements Initializable {
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;


    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;


    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private ScrollPane scrollPaneItems;


    @FXML
    private HBox boxItems;

    public static BlogArticles article = null;
    public static int ida;
    @FXML
    private Button home;

    public static BlogArticles thisArticle;
    private Connection cnx;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BlogsService Service = new BlogsService();
        List<BlogArticles> articles = BlogsService.listerArticles();
        Node[] nodes = new Node[articles.size()];
        for (int i = 0; i < nodes.length; i++) {
            //       ida= (articles.get(i)).getId();
            try {
                final int j = i;
                article = articles.get(i);
                nodes[i] = FXMLLoader.load(getClass().getResource("ItemB.fxml"));
                //give the items some effect
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });

                int finalI = i;
                nodes[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        thisArticle = articles.get(finalI);
                    }
                });

                boxItems.getChildren().add(nodes[i]);
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
        if (actionEvent.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }




    public void backhome(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root2 = loader.load();


        home.getScene().setRoot(root2);
    }



}
