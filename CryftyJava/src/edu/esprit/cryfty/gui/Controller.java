package edu.esprit.cryfty.gui;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Button btnArticles;
    @FXML
    private Pane pnlArticles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<BlogArticles> list = (ArrayList<BlogArticles>) BlogsService.listerArticles();
        int n;
        n=list.size();
        Node[] nodes = new Node[n];
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


    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
             pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();

            Node n = FXMLLoader.load(getClass().getResource("fxml/ListeArticles.fxml"));

            pnlMenus.getChildren().add(n);


        }
        if (actionEvent.getSource() == btnArticles) {
            pnlArticles.setStyle("-fx-background-color : #53639F");
            pnlArticles.toFront();
         //   articlebo();
            Node n = FXMLLoader.load(getClass().getResource("fxml/boarticle.fxml"));

            pnlArticles.getChildren().add(n);


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
 public  void articlebo(){
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
    for(int i=0;i<articles.size();i++ ){
        table.getItems().add(articles.get(i));
    }



     Stage primaryStage = new Stage();
    root.setCenter(table);
     Scene scene = new Scene(root, 500, 300);
     primaryStage.setTitle("TableView Demo");
     primaryStage.setScene(scene);
     primaryStage.show();

    }

}
