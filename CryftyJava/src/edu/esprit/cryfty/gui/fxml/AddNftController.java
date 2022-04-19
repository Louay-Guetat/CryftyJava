package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.service.NodeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static edu.esprit.cryfty.gui.Main.currentUser;

public class AddNftController implements Initializable {
    @FXML
    private TextField tfTitle;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;
    @FXML
    private TextArea taDescription;
    @FXML
    private Label lblPrice;
    @FXML
    private TextField tfPrice;
    @FXML
    private Label lblCurrency;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblSubCategory;
    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox cbCategory;
    @FXML
    private ComboBox cbSubCategory;
    @FXML
    private ComboBox cbCurrency;
    @FXML
    private ImageView imNft;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.showCategories();
        String [] categoryNames = new String[categories.size()];
        for (int i=0;i<categories.size(); i++){
            categoryNames[i] = categories.get(i).getName();
        }
        ObservableList<String> list = FXCollections.observableArrayList(categoryNames);
        cbCategory.setItems(list);

        NodeService nodeService = new NodeService();
        List<Node> nodes = nodeService.getNodes();
        String[]coinCodes = new String[nodes.size()];
        for(int i=0; i<nodes.size();i++){
            coinCodes[i] = nodes.get(i).getCoinCode();
        }
        ObservableList<String> nodeList = FXCollections.observableArrayList(coinCodes);
        cbCurrency.setItems(nodeList);

        /*SubCategoryService subCategoryService = new SubCategoryService();
        List<SubCategory> subCategories = new ArrayList();
        String[] subCategoryNames = new String[subCategories.size()];
        for(int i=0;i<subCategories.size();i++){
            Category category = subCategories.get(i).getCategory();
            if(category.getName().equals(cbCategory.getValue())){
                subCategoryNames
            }
        }*/
        /*tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*,\\d*")) {
                    tfPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });*/

        tfPrice.addEventFilter(KeyEvent.KEY_TYPED, priceFilter());

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CategoryService categoryService = new CategoryService();
                SubCategoryService subCategoryService = new SubCategoryService();
                NodeService nodeService = new NodeService();

                Category category = new Category();
                SubCategory subCategory = new SubCategory();
                Node node = new Node();

                category = categoryService.findCategoryByName(cbCategory.getValue().toString());
                subCategory = subCategoryService.findSubCategoryByName(cbSubCategory.getValue().toString());
                node = nodeService.getNodeByName(cbCurrency.getValue().toString());

                Nft nft = new Nft();
                nft.setImage("");

                nft.setTitle(tfTitle.getText());
                nft.setDescription(taDescription.getText());
                nft.setPrice(Float.parseFloat(tfPrice.getText()));
                nft.setCategory(category);
                nft.setSubCategory(subCategory);
                nft.setCurrency(node);
                nft.setOwner((Client)currentUser);

                Date now = new Date();
                nft.setCreationDate(now);
                nft.setLikes(0);

                String ch = imNft.getImage().impl_getUrl();
                String fileName = ch.substring(6,ch.length());
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                String newFileName = getUniqid(extension);
                File sourceFile = new File(fileName);
                File destinationFile = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\"+newFileName);
                try{
                    Files.copy(sourceFile.toPath(),destinationFile.toPath());
                }catch (IOException ex){
                    System.out.println(ex.getMessage());
                }

                nft.setImage(newFileName);

                NftService nftService = new NftService();
                nftService.addNft(nft);

                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();
            }
        });
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

    @FXML
    public void addImage(ActionEvent actionEvent) {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your NFT");
        fileChooser.setInitialDirectory(new File("C:\\Users\\LOUAY\\Desktop\\NftsTest"));
        fileChooser.getExtensionFilters().add(imageFilter);
        Image image = null;
        try{
            image = new Image(fileChooser.showOpenDialog(null).toURI().toString());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        if(image != null){
            imNft.setImage(image);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No NFT Selected");
            alert.setHeaderText("Please select an NFT");
            alert.showAndWait();
        }
    }

    @FXML
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

    public String getUniqid(String extension){
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+extension;
    }
}