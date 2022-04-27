package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.service.NodeService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExploreController implements Initializable {
    @FXML
    private Pane pnlItems;
    @FXML
    private VBox boxItems;
    @FXML
    private Pane pnlFiltres;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private RadioButton rbCroissant;
    @FXML
    private RadioButton rbPrixNone;
    @FXML
    private RadioButton rbDecroissant;
    @FXML
    private TextField tfSearch;
    @FXML
    private RadioButton rbPertinenceNone;
    @FXML
    private RadioButton rbPertinanceDec;
    @FXML
    private RadioButton rbPertinanceCoirssant;
    @FXML
    private Slider sldPriceMax;
    @FXML
    private Slider sldPriceMin;
    @FXML
    private CheckComboBox cbCategory;
    @FXML
    private CheckComboBox cbSubCategory;
    @FXML
    private CheckComboBox cbCurrency;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnBack;
    @FXML
    private TextField tfPrixMax;
    @FXML
    private TextField tfPrixMin;
    @FXML
    private Button btnClear;

    public static Nft nft1 = null;
    public static Nft nftClicked= null;
    private List<Nft> nfts = new NftService().showNfts();
    private final ToggleGroup groupPrice = new ToggleGroup();
    private final ToggleGroup groupLikes = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfPrixMax.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, priceFilter());
        tfPrixMin.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, priceFilter());

        tfPrixMax.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if(event.getCode().getName().equals("Enter")){
                    sldPriceMax.setValue(Double.valueOf(tfPrixMax.getText().replace(",",".")));
                }

            }
        });

        tfPrixMin.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if(event.getCode().getName().equals("Enter")){
                    sldPriceMin.setValue(Double.valueOf(tfPrixMin.getText().replace(",",".")));
                }
            }
        });

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        rbCroissant.setToggleGroup(groupPrice);
        rbDecroissant.setToggleGroup(groupPrice);
        rbPrixNone.setToggleGroup(groupPrice);

        rbPertinanceCoirssant.setToggleGroup(groupLikes);
        rbPertinanceDec.setToggleGroup(groupLikes);
        rbPertinenceNone.setToggleGroup(groupLikes);

        List<Category> categories = new CategoryService().showCategories();
        final ObservableList<String> categoryList = FXCollections.observableArrayList();
        for(int i=0;i<categories.size();i++){
            categoryList.add(categories.get(i).getName());
        }
        cbCategory.getItems().addAll(categoryList);

            List<SubCategory> subCategories = new SubCategoryService().showSubCategories();
            final ObservableList<String> subCategoryList = FXCollections.observableArrayList();
            for(int i=0; i<subCategories.size();i++){
                subCategoryList.add(subCategories.get(i).getName());
            }
            cbSubCategory.getItems().addAll(subCategoryList);

        List<edu.esprit.cryfty.entity.Node> currencies = new NodeService().getNodes();
        final ObservableList<String> currencyList = FXCollections.observableArrayList();
        for(int i=0; i<currencies.size();i++){
            currencyList.add(currencies.get(i).getNodeLabel());
        }
        cbCurrency.getItems().addAll(currencyList);

        sldPriceMax.setShowTickMarks(true);
        sldPriceMax.setShowTickLabels(true);
        sldPriceMax.setBlockIncrement(50);
        sldPriceMax.setMajorTickUnit(200);
        sldPriceMax.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                DecimalFormat df = new DecimalFormat("0.00");
                tfPrixMax.setText(df.format(newValue));
            }
        });

        sldPriceMin.setMin(0);
        sldPriceMin.setShowTickMarks(true);
        sldPriceMin.setShowTickLabels(true);
        sldPriceMin.setBlockIncrement(10);
        sldPriceMin.setMajorTickUnit(200);
        sldPriceMin.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                DecimalFormat df = new DecimalFormat("0.00");
                tfPrixMin.setText(df.format(newValue));
                sldPriceMax.setMin(sldPriceMin.getValue());
            }
        });
        try {
            createView(nfts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createView(List<Nft> nfts) throws IOException {
        if(!nfts.isEmpty()){
            sldPriceMax.setMax(getMax(nfts));
            sldPriceMin.setMax(getMax(nfts));
            sldPriceMax.setValue(sldPriceMax.getMax());
            tfPrixMax.setText(sldPriceMax.getValue()+"");
            tfPrixMin.setText(sldPriceMin.getValue()+"");

            Node [] nodes = new Node[nfts.size()];
            HBox hBox = new HBox();
            int i=0;
            do{
                if(i%2 == 0){
                    hBox = new HBox();
                    nft1 = nfts.get(i);
                    nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                    hBox.getChildren().add(nodes[i]);
                    i++;
                    boxItems.getChildren().add(hBox);
                }
                else{
                    nft1 = nfts.get(i);
                    nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                    hBox.getChildren().add(nodes[i]);
                    i++;
                }
            }while(i<nfts.size());
        }
        else{
            Label message = new Label("No nfts to show");
            boxItems.getChildren().clear();
            boxItems.getChildren().add(message);
        }
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource() == btnSubmit) {
            if(tfSearch.getText().isEmpty()){
                appliquerFiltre(nfts);
            }
            else{
                appliquerFiltre(search());
            }
        }
        if(actionEvent.getSource() == btnBack){
            Scene scene = btnBack.getScene();
            nft1 = null;
            scene.getWindow().hide();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

        if(actionEvent.getSource() == btnClear){
            sldPriceMax.setValue(0);
            sldPriceMin.setValue(0);
            rbPrixNone.setSelected(true);
            rbPertinenceNone.setSelected(true);
            cbCategory.getCheckModel().clearChecks();
            cbSubCategory.getCheckModel().clearChecks();
            cbCurrency.getCheckModel().clearChecks();
            tfPrixMin.setText("");
            tfPrixMax.setText("");
            tfSearch.setText("");
            boxItems.getChildren().clear();
            createView(nfts);
        }
    }

    public float getMax(List<Nft> nfts){
     float max = nfts.get(0).getPrice();
        for(int i=1;i<nfts.size();i++){
            if(nfts.get(i).getPrice()>max){
                max = nfts.get(i).getPrice();
            }
        }
     return max;
    }

    public static EventHandler<javafx.scene.input.KeyEvent> priceFilter() {

        EventHandler<javafx.scene.input.KeyEvent> aux = new EventHandler<javafx.scene.input.KeyEvent>() {
            public void handle(javafx.scene.input.KeyEvent keyEvent) {
                if (!"0123456789.,".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        };
        return aux;
    }

    public void appliquerFiltre(List<Nft> nfts) throws IOException {
        boxItems.getChildren().clear();
        ObservableList<String> listCategories = cbCategory.getCheckModel().getCheckedItems();
        ObservableList<String> listSubCategories = cbSubCategory.getCheckModel().getCheckedItems();
        ObservableList<String> listCurrencies = cbCurrency.getCheckModel().getCheckedItems();
        List<Nft> nftFiltrer = new ArrayList<>();

        //appliquer le filtre
        if(listCategories.size()!=0){
            nftFiltrer = filtrerParCategory(nfts);
            if(listSubCategories.size()!=0){
                nftFiltrer = filtrerParSubCategory(nftFiltrer);
                if(listCurrencies.size() != 0){
                    nftFiltrer = filtrerParCurrency(nftFiltrer);
                }
            }
        }
        else{
            if(listSubCategories.size() != 0){
                nftFiltrer = filtrerParSubCategory(nfts);
                if(listCurrencies.size() != 0){
                    nftFiltrer = filtrerParCurrency(nftFiltrer);
                }
            }
            else if(listCurrencies.size() != 0){
                nftFiltrer = filtrerParCurrency(nfts);
            }
        }

        Double prixMin = sldPriceMin.getValue();
        Double prixMax = sldPriceMax.getValue();

        if(nftFiltrer.isEmpty()){
            for(int i=0; i<nfts.size(); i++) {
                nft1 = nfts.get(i);
                if (nft1.getPrice() >= prixMin && nft1.getPrice() <= prixMax) {
                    nftFiltrer.add(nft1);
                    System.out.println("hello");
                }
            }
        }
        else{
            int i=0;
            while(!nftFiltrer.isEmpty() && i<nftFiltrer.size()){
                if(nftFiltrer.get(i).getPrice() < prixMin || nftFiltrer.get(i).getPrice() > prixMax){
                    nftFiltrer.remove(nftFiltrer.get(i));
                }
                else{
                    i++;
                }
            }
        }

        if(nftFiltrer.isEmpty()){
            if(groupPrice.getSelectedToggle() == rbCroissant){
                nftFiltrer = nfts.stream().sorted(Comparator.comparingDouble(Nft::getPrice)).collect(Collectors.toList());
            }
            if(groupPrice.getSelectedToggle() == rbDecroissant){
                nftFiltrer = nfts.stream().sorted(Comparator.comparingDouble(Nft::getPrice).reversed()).collect(Collectors.toList());
            }
        }
        else{
            if(groupPrice.getSelectedToggle() == rbCroissant){
                nftFiltrer = nftFiltrer.stream().sorted(Comparator.comparingDouble(Nft::getPrice)).collect(Collectors.toList());
            }
            if(groupPrice.getSelectedToggle() == rbDecroissant){
                nftFiltrer = nftFiltrer.stream().sorted(Comparator.comparingDouble(Nft::getPrice).reversed()).collect(Collectors.toList());
            }
        }
        //filtre fait

        Node[] nodes = new Node[nftFiltrer.size()];
        HBox hBox = new HBox();

        int i = 0;
        if (listCategories.isEmpty() && listSubCategories.isEmpty() && listCurrencies.isEmpty() && prixMin==0 && prixMax==getMax(nfts)
                && groupPrice.getSelectedToggle() == rbPrixNone && groupLikes.getSelectedToggle() == rbPertinenceNone) {
            createView(nfts);
        }
        else {
            if(nftFiltrer.isEmpty()){
                Label lbl = new Label("Aucun produit.");
                boxItems.getChildren().add(lbl);
            }
            else{
                do {
                    nft1 = nftFiltrer.get(i);
                    if (i % 2 == 0) {
                        hBox = new HBox();
                        nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                        hBox.getChildren().add(nodes[i]);
                        boxItems.getChildren().add(hBox);
                        i++;
                    } else {
                        nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                        hBox.getChildren().add(nodes[i]);
                        i++;
                    }
                } while (i < nftFiltrer.size());
            }
        }
    }

    public List<Nft> filtrerParCategory(List<Nft> nfts){
        ObservableList<String> list = cbCategory.getCheckModel().getCheckedItems();
        List<Nft> nftList = new ArrayList<>();
        for (int i=0; i< nfts.size();i++){
            if(list.contains(nfts.get(i).getCategory().getName())){
                nftList.add(nfts.get(i));
            }
        }
        return nftList;
    }

    public List<Nft> filtrerParSubCategory(List<Nft> nfts){
        ObservableList<String> list = cbSubCategory.getCheckModel().getCheckedItems();
        List<Nft> nftList = new ArrayList<>();
        for (int i=0; i< nfts.size();i++){
            if(list.contains(nfts.get(i).getSubCategory().getName())){
                nftList.add(nfts.get(i));
            }
        }
        return nftList;
    }

    public List<Nft> filtrerParCurrency(List<Nft> nfts){
        ObservableList<String> list = cbCurrency.getCheckModel().getCheckedItems();
        List<Nft> nftList = new ArrayList<>();
        for (int i=0; i< nfts.size();i++){
            if(list.contains(nfts.get(i).getCurrency().getNodeLabel())){
                nftList.add(nfts.get(i));
            }
        }
        return nftList;
    }

    public List<Nft> search() throws IOException {

     // Wrap the ObservableList in a FilteredList (initially display all data).
        NftService nftService = new NftService();
        FilteredList<Nft> filteredData = new FilteredList(nftService.getNftsByTitle(tfSearch.getText()), b -> true);
        System.out.println(nftService.getNftsByTitle(tfSearch.getText()));
        // 2. Set the filter Predicate whenever the filter changes.
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nft -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();


                if (String.valueOf(nft.getTitle()).contains(lowerCaseFilter))
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Nft> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        boxItems.getChildren().clear();
        createView(sortedData.stream().collect(Collectors.toList()));
        return(sortedData.stream().collect(Collectors.toList()));
    }
}


