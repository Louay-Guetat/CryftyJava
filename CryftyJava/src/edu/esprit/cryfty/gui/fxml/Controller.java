package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.util.Callback;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
    @FXML
    private AnchorPane view;
    @FXML
    private Pane pnlItem;
    @FXML
    private TableView tbvCategory;
    @FXML
    private TableView tbvSubCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createView();
        boxItems.setPrefWidth(1020);
        boxItems.setPrefHeight(344);
        scrollPaneItems.setContent(boxItems);

        ImageView add = new ImageView();
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\add.png");
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
            Node node = FXMLLoader.load(getClass().getResource("Home.fxml"));
            view.getChildren().clear();
            view.getChildren().add(node);
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
        if(actionEvent.getSource()==btnAddNft){
            Scene scene = btnAddNft.getScene();
            scene.getWindow().hide();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddNft.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

        if(actionEvent.getSource()==btnSettings){
            pnlItem.getChildren().clear();
            initiateCategories();
            initiateSubCategories();
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
                        nftClicked = nfts.get(finalI);
                        Node node = nodes[finalI];
                        try {
                            node = FXMLLoader.load(getClass().getResource("OneItem.fxml"));
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

    public void initiateCategories() throws IOException {
        TableView<Category> tableView = new TableView<>();
        tableView.setEditable(true);
        TableColumn<Category,String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        TableColumn<Category, Date> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        TableColumn<Category,String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        TableColumn<Category,String> nbrSubCat = new TableColumn("Number of subCategories");
        nbrSubCat.setPrefWidth(150);
        TableColumn<Category,Void> update = new TableColumn<>("Update");
        update.setPrefWidth(106);
        TableColumn<Category,Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(106);

        tableView.getColumns().addAll(name,creationDate,nbrNfts,nbrSubCat,update,delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<Category> forTableColumn());
        name.setOnEditCommit((TableColumn.CellEditEvent<Category, String> event) -> {
            TablePosition<Category, String> pos = event.getTablePosition();

            String newName = event.getNewValue();

            int row = pos.getRow();
            Category category = event.getTableView().getItems().get(row);

            category.setName(newName);
        });
        creationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        nbrNfts.setCellValueFactory(new PropertyValueFactory<>("nbrNfts"));
        nbrSubCat.setCellValueFactory(new PropertyValueFactory<>("nbrSubCategories"));

        Callback<TableColumn<Category, Void>, TableCell<Category, Void>> cellUpdate = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {

                    private final Button btn = new Button("Update");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Category data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
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
        update.setCellFactory(cellUpdate);

        Callback<TableColumn<Category, Void>, TableCell<Category, Void>> cellDelete = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Category data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
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
        for(int i=0; i<categories.size();i++){
            list.add(categories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setFixedCellSize(30);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlItem.getChildren().add(tableView);
        tableView.setLayoutY(50);
    }

    public void initiateSubCategories(){
        TableView<SubCategory> tableView = new TableView<SubCategory>();
        tableView.setEditable(true);
        TableColumn<SubCategory,String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        TableColumn<SubCategory, Date> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        TableColumn<SubCategory,String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        TableColumn<SubCategory, String> category = new TableColumn<SubCategory, String>("Category");
        category.setPrefWidth(150);

        TableColumn<SubCategory,Void> update = new TableColumn<>("Update");
        update.setPrefWidth(106);
        TableColumn<SubCategory,Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(106);

        tableView.getColumns().addAll(name,creationDate,nbrNfts,category,update,delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<SubCategory> forTableColumn());

        name.setOnEditCommit((TableColumn.CellEditEvent<SubCategory, String> event) -> {
            TablePosition<SubCategory, String> pos = event.getTablePosition();

            String newName = event.getNewValue();

            int row = pos.getRow();
            SubCategory person = event.getTableView().getItems().get(row);

            person.setName(newName);
        });

        creationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        nbrNfts.setCellValueFactory(new PropertyValueFactory<>("nbrNfts"));

        CategoryService categoryService = new CategoryService();
        List<Category> categoryList = categoryService.showCategories();
        String[] categoryNames = new String[categoryList.size()];
        for (int i=0; i<categoryList.size();i++){
            categoryNames[i] = categoryList.get(i).getName();
        }

        ObservableList<String> genderList = FXCollections.observableArrayList(categoryNames);
        category.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SubCategory, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SubCategory, String> param) {
                SubCategory subCategory = param.getValue();
                // F,M
                String categoryName = subCategory.getCategory().getName();
                Category cat = categoryService.findCategoryByName(categoryName);
                return new SimpleObjectProperty<String>(cat.getName());
            }
        });
        category.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));

        Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>> cellUpdate = new Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>>() {
            @Override
            public TableCell<SubCategory, Void> call(final TableColumn<SubCategory, Void> param) {
                final TableCell<SubCategory, Void> cell = new TableCell<SubCategory, Void>() {

                    private final Button btn = new Button("Update");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            SubCategory data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
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
        update.setCellFactory(cellUpdate);

        Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>> cellDelete = new Callback<TableColumn<SubCategory, Void>, TableCell<SubCategory, Void>>() {
            @Override
            public TableCell<SubCategory, Void> call(final TableColumn<SubCategory, Void> param) {
                final TableCell<SubCategory, Void> cell = new TableCell<SubCategory, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            SubCategory data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
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
        for(int i=0; i<subCategories.size();i++){
            list.add(subCategories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setFixedCellSize(30);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlItem.getChildren().add(tableView);
        tableView.setLayoutY(240);
    }

}
