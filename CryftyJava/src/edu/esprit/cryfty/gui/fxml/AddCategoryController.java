package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class AddCategoryController implements Initializable {
    @FXML
    private TextField tfCategoryName;
    @FXML
    private ComboBox cbCategory;
    @FXML
    private TextField tfSubCategoryName;
    @FXML
    private Button btnAddCategory;
    @FXML
    private Button btnAddSubCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.showCategories();
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        for (int i=0; i<categories.size();i++){
            categoryList.add(categories.get(i).getName());
        }

        cbCategory.setItems(categoryList);
    }

    @FXML
    public void addCategory(){

        if(tfCategoryName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");

            alert.setHeaderText("Please specify the name of the Category.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional confirmation = alert.showAndWait();
            if(confirmation.get() == ButtonType.CANCEL){
                System.out.println("Cancel");
            }
            else if(confirmation.get() == ButtonType.OK){
                Category category = new Category();

                category.setName(tfCategoryName.getText());
                category.setCreationDate(LocalDateTime.now());
                category.setNbrSubCategories(0);
                category.setNbrNfts(0);

                CategoryService categoryService = new CategoryService();
                categoryService.addCategory(category);
                tfCategoryName.clear();
            }
        }
    }

    @FXML
    public void addSubCategory(){
        if(tfSubCategoryName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please specify the name of the SubCategory.");
            alert.showAndWait();
        }
        else if(cbCategory.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("You need to chooser a Category.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional confirmation = alert.showAndWait();

            if(confirmation.get() == ButtonType.CANCEL){
                System.out.println("Cancel");
            }
            else if (confirmation.get() == ButtonType.OK){
                SubCategory subCategory = new SubCategory();
                subCategory.setName(tfSubCategoryName.getText());
                CategoryService categoryService = new CategoryService();
                subCategory.setCategory(categoryService.findCategoryByName(cbCategory.getValue().toString()));
                subCategory.setNbrNfts(0);
                subCategory.setCreationDate(LocalDateTime.now());

                SubCategoryService subCategoryService = new SubCategoryService();
                subCategoryService.addSubCategory(subCategory);
                tfSubCategoryName.clear();
            }
        }
    }

}
