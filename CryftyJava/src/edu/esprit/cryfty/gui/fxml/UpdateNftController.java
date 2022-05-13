package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.service.NodeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Controller.nftClicked;
import static edu.esprit.cryfty.gui.Main.stage;


public class UpdateNftController implements Initializable {
    @javafx.fxml.FXML
    private Label lblCategory;
    @javafx.fxml.FXML
    private TextArea taDescription;
    @javafx.fxml.FXML
    private TextField tfPrice;
    @javafx.fxml.FXML
    private ComboBox cbCategory;
    @javafx.fxml.FXML
    private Label lblDescription;
    @javafx.fxml.FXML
    private ComboBox cbSubCategory;
    @javafx.fxml.FXML
    private ComboBox cbCurrency;
    @javafx.fxml.FXML
    private AnchorPane anchorPane;
    @javafx.fxml.FXML
    private TextField tfTitle;
    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private Label lblSubCategory;
    @javafx.fxml.FXML
    private Label lblPrice;
    @javafx.fxml.FXML
    private Label lblCurrency;
    @javafx.fxml.FXML
    private Label lblTitle;
    @javafx.fxml.FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView back = new ImageView();
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\back.png");
            Image image = new Image(inputstream);
            back.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        btnReturn.setGraphic(back);

        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.showCategories();
        String [] categoryNames = new String[categories.size()];
        for (int i=0;i<categories.size(); i++){
            categoryNames[i] = categories.get(i).getName();
        }
        ObservableList<String> Categorylist = FXCollections.observableArrayList(categoryNames);
        cbCategory.setItems(Categorylist);

        int j=0;
        SubCategoryService subCategoryService = new SubCategoryService();
        List<SubCategory> subCategories = subCategoryService.showSubCategories();

        String[] subCategoryNames = new String[nftClicked.getCategory().getNbrSubCategories()];
        for(int i=0;i<subCategories.size();i++){
            Category cat = subCategories.get(i).getCategory();
            if(cat.getName().equals(nftClicked.getCategory().getName())){
                subCategoryNames[j] = subCategories.get(i).getName();
                j++;
            }
        }
        ObservableList<String> subCategoryList = FXCollections.observableArrayList(subCategoryNames);
        cbSubCategory.setItems(subCategoryList);

        NodeService nodeService = new NodeService();
        List<Node> nodes = nodeService.getNodes();
        String[]coinCodes = new String[nodes.size()];
        for(int i=0; i<nodes.size();i++){
            coinCodes[i] = nodes.get(i).getCoinCode();
        }
        ObservableList<String> nodeList = FXCollections.observableArrayList(coinCodes);
        cbCurrency.setItems(nodeList);
        tfPrice.addEventFilter(KeyEvent.KEY_TYPED, priceFilter());

        tfTitle.setText(nftClicked.getTitle());
        taDescription.setText(nftClicked.getDescription());
        tfPrice.setText(nftClicked.getPrice()+"");
        cbCategory.setValue(nftClicked.getCategory().getName());
        cbSubCategory.setValue(nftClicked.getSubCategory().getName());
        cbCurrency.setValue(nftClicked.getCurrency().getCoinCode());

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tfTitle.getText().length() < 3 || tfTitle.getText().length()>10){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please write a good title");
                    alert.showAndWait();
                }
                else if(cbCurrency.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a currency");
                    alert.showAndWait();
                }
                else if(cbCategory.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a category");
                    alert.showAndWait();
                }
                else if (cbSubCategory.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a subCategory");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    Optional confirmation = alert.showAndWait();
                    if(confirmation.get() == ButtonType.CANCEL){
                        System.out.println("Cancel");
                    }
                    else if(confirmation.get() == ButtonType.OK){
                        nftClicked.setTitle(tfTitle.getText());
                        nftClicked.setDescription(taDescription.getText());
                        nftClicked.setPrice(Float.parseFloat(tfPrice.getText()));
                        nftClicked.setCategory(categoryService.findCategoryByName(cbCategory.getValue().toString()));
                        nftClicked.setSubCategory(subCategoryService.findSubCategoryByName(cbSubCategory.getValue().toString()));
                        nftClicked.setCurrency(nodeService.getNodeByName(cbCurrency.getValue().toString()));

                        NftService nftService = new NftService();
                        nftService.updateNft(nftClicked);

                        Scene scene = btnSubmit.getScene();
                        scene.getWindow().hide();
                        Parent root = null;
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
                            root = fxmlLoader.load();
                            Controller controller = fxmlLoader.getController();
                            controller.setPane("OneItem.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
            }
        });
    }

    @javafx.fxml.FXML
    public void onCategoryClick(ActionEvent actionEvent) {
        int j=0;
        CategoryService categoryService = new CategoryService();
        Category category = categoryService.findCategoryByName(cbCategory.getValue().toString());
        SubCategoryService subCategoryService = new SubCategoryService();
        List<SubCategory> subCategories = subCategoryService.showSubCategories();

        String[] subCategoryNames = new String[category.getNbrSubCategories()];
        for(int i=0;i<subCategories.size();i++){
            Category cat = subCategories.get(i).getCategory();
            if(cat.getName().equals(cbCategory.getValue())){
                subCategoryNames[j] = subCategories.get(i).getName();
                j++;
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(subCategoryNames);
        cbSubCategory.setItems(list);
    }

    public static EventHandler<KeyEvent> priceFilter() {

        EventHandler<KeyEvent> aux = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789.".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        };
        return aux;
    }
    @javafx.fxml.FXML
    public void goBack() throws IOException {
        Scene scene = lblTitle.getScene();
        scene.getWindow().hide();
        stage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setPane("OneItem.fxml");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
