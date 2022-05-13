package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.service.user.ClientService;
import edu.esprit.cryfty.service.user.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static edu.esprit.cryfty.gui.Main.stage;



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

        tfPrice.addEventFilter(KeyEvent.KEY_TYPED, priceFilter());

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(imNft.getImage().impl_getUrl().equals("file:/C:/Users/LOUAY/Desktop/CryftyJava/CryftyJava/out/production/CryftyJava/edu/esprit/cryfty/images/Nfts/default.png")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please upload an Image");
                    alert.showAndWait();
                    btnImg.requestFocus();
                }
                else if(tfTitle.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("You need to specify a title.");
                    alert.showAndWait();
                    tfTitle.requestFocus();
                }
                else if (tfTitle.getText().length() < 3 || tfTitle.getText().length()>20){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("The length of the title must be between 3 and 20 character.");
                    alert.showAndWait();
                    tfTitle.requestFocus();
                    tfTitle.selectAll();
                }
                else if (tfPrice.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("You need to specify a price.");
                    alert.showAndWait();
                    tfPrice.requestFocus();
                }
                else if(cbCurrency.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a currency.");
                    alert.showAndWait();
                    cbCurrency.requestFocus();
                }
                else if(cbCategory.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a category.");
                    alert.showAndWait();
                    cbCategory.requestFocus();
                }
                else if (cbSubCategory.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please choose a subCategory.");
                    alert.showAndWait();
                    cbSubCategory.requestFocus();
                }
                 else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    Optional confirmation = alert.showAndWait();
                    if(confirmation.get() == ButtonType.CANCEL){
                        System.out.println("cancel");
                    }
                    else if(confirmation.get() == ButtonType.OK){
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
                        ClientService clientService = new ClientService();
                        Client client = clientService.getClientById(Session.getInstance().getCurrentUser().getId());
                        nft.setOwner(client);

                        LocalDateTime now = LocalDateTime.now();
                        nft.setCreationDate(now);
                        nft.setLikes(0);

                        String ch = imNft.getImage().impl_getUrl();
                        String fileName = ch.substring(6, ch.length());
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        String newFileName = getUniqid(extension);
                        File sourceFile = new File(fileName);
                        File destinationFile = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\" + newFileName);
                        try {
                            System.out.println(sourceFile + "\n" + destinationFile);
                            Files.copy(sourceFile.toPath(), destinationFile.toPath());
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }

                        nft.setImage(newFileName);
                        NftService nftService = new NftService();
                        nftService.addNft(nft);

                        Scene scene = btnSubmit.getScene();
                        scene.getWindow().hide();
                        Parent root = null;
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
                            root = fxmlLoader.load();
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

    public void goBack() throws IOException {
        Scene scene = lblTitle.getScene();
        scene.getWindow().hide();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}