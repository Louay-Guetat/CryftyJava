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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Main.stage;

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
            /*pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
            pnlCustomer.setVisible(false);*/
            Scene scene = btnMenus.getScene();
            scene.getWindow().hide();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Explore.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
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
            pnlItem.getChildren().clear();
            Node node = new FXMLLoader().load(getClass().getResource("AddNft.fxml"));
            node.setLayoutX(80);
            node.setLayoutY(20);
            pnlItem.getChildren().add(node);
        }

        if(actionEvent.getSource()==btnSettings){
            pnlItem.getChildren().clear();
            Button btnStats = new Button("Check Stats");
            pnlItem.getChildren().add(btnStats);
            btnStats.setLayoutX(900);
            btnStats.setLayoutY(25);
            btnStats.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pnlItem.getChildren().clear();
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("Gchart.fxml"));
                        System.out.println("done");
                        pnlItem.getChildren().add(node);

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            initiateCategories();
            initiateSubCategories();
            Button btnCategory = new Button("Add");
            pnlItem.getChildren().add(btnCategory);
            btnCategory.setLayoutY(25);
            btnCategory.setLayoutX(15);
            btnCategory.setPrefWidth(100);

            btnCategory.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Node node = new FXMLLoader().load(getClass().getResource("AddCategory.fxml"));
                        pnlItem.getChildren().clear();
                        pnlItem.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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
        tableView.setStyle("-fx-background-color: transparent;");
        TableColumn<Category,String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        name.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category, LocalDateTime> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        creationDate.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category,String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        nbrNfts.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category,String> nbrSubCat = new TableColumn("Number of subCategories");
        nbrSubCat.setPrefWidth(150);
        nbrSubCat.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<Category,Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(200);
        delete.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-alignment: CENTER;");
        delete.setResizable(false);

        tableView.getColumns().addAll(name,creationDate,nbrNfts,nbrSubCat,delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<Category> forTableColumn());
        name.setOnEditCommit((TableColumn.CellEditEvent<Category, String> event) -> {
            TablePosition<Category, String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            Category category = event.getTableView().getItems().get(row);
            if(!newName.isEmpty()){
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
                    if(empty) {
                        setText(null);
                    }
                    else {
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
                            if(category.getNbrSubCategories() ==0 && category.getNbrNfts() ==0 ){
                                CategoryService categoryService = new CategoryService();
                                categoryService.deleteCategory(category);
                            }
                            else{
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
        for(int i=0; i<categories.size();i++){
            list.add(categories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setMaxSize(1020,180);
        tableView.setFixedCellSize(40);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlItem.getChildren().add(tableView);
        tableView.setLayoutY(75);
    }

    public void initiateSubCategories(){
        TableView<SubCategory> tableView = new TableView<SubCategory>();
        tableView.setEditable(true);
        tableView.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        TableColumn<SubCategory,String> name = new TableColumn("Name");
        name.setPrefWidth(300);
        name.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-selection-bar: red;");
        TableColumn<SubCategory, LocalDateTime> creationDate = new TableColumn("Creation Date");
        creationDate.setPrefWidth(200);
        creationDate.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<SubCategory,String> nbrNfts = new TableColumn("Number of Nfts");
        nbrNfts.setPrefWidth(150);
        nbrNfts.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");
        TableColumn<SubCategory, String> category = new TableColumn<SubCategory, String>("Category");
        category.setPrefWidth(150);
        category.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171;");

        TableColumn<SubCategory,Void> delete = new TableColumn<>("Delete");
        delete.setPrefWidth(200);
        delete.setStyle("-fx-background-color: #02030A; -fx-text-fill: white; -fx-border-color: #4e7171; -fx-alignment: CENTER;");
        delete.setResizable(false);

        tableView.getColumns().addAll(name,creationDate,nbrNfts,category,delete);

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name.setCellFactory(TextFieldTableCell.<SubCategory> forTableColumn());

        name.setOnEditCommit((TableColumn.CellEditEvent<SubCategory, String> event) -> {
            TablePosition<SubCategory, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            SubCategory subCategory = event.getTableView().getItems().get(row);
            if(!newName.isEmpty()){
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
                    if(empty) {
                        setText(null);
                    }
                    else {
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
        for (int i=0; i<categoryList.size();i++){
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
            if(!newName.isEmpty()){
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
                            if(subCategory.getNbrNfts()==0){
                                SubCategoryService subCategoryService = new SubCategoryService();
                                subCategoryService.deleteSubCategory(subCategory);
                            }
                            else{
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
        for(int i=0; i<subCategories.size();i++){
            list.add(subCategories.get(i));
        }
        tableView.setItems(list);
        tableView.setPrefHeight(344);
        tableView.setPrefWidth(1020);
        tableView.setMaxSize(1020,180);
        tableView.setFixedCellSize(40);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));

        pnlItem.getChildren().add(tableView);
        tableView.setLayoutY(280);
    }
}
