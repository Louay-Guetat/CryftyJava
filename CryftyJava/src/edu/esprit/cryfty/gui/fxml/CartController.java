package edu.esprit.cryfty.gui.fxml;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.service.payment.TransactionService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Nft, String> descNft;
    @javafx.fxml.FXML
    private TableColumn<Nft, String> PriceNft;
    @javafx.fxml.FXML
    private TableColumn<Nft, String> titleNft;
    @FXML
    private TableView<Nft> TableCart;
    @FXML
    private Label totalId;
    @FXML
    private TableColumn<Nft, String> ActionNft;

    ObservableList<Nft>  CartNftList = FXCollections.observableArrayList();
    @FXML
    private TextField searchId;
 

    @FXML
    private Button cartWalletBtn;
    @FXML
    private Button StripebtnWallet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateTable();
        search();

    }
    public void updateTable()
    {
        CartService cartService=new CartService();
        //PriceNft.setStyle("-fx-text-fill : white;");
        PriceNft.setCellValueFactory(new PropertyValueFactory<>("price"));
        descNft.setCellValueFactory(new PropertyValueFactory<>("description"));
        titleNft.setCellValueFactory(new PropertyValueFactory<>("title"));


        Callback<TableColumn<Nft, String>, TableCell<Nft, String>> cellFoctory = (TableColumn<Nft, String> param) -> {

            // make cell containing buttons
            final TableCell<Nft, String> cell = new TableCell<Nft, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);


                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:20px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to delete this nft from your cart?");
                            Optional <ButtonType> action =alert.showAndWait();
                            if(action.get()== ButtonType.OK)
                            {
                                Nft nft= TableCart.getSelectionModel().getSelectedItem();
                                cartService.supprimerNftFromCart(nft.getId());
                                updateTable();
                            }
                        });
                        HBox managebtn = new HBox( deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };

            return cell;
        };
        ActionNft.setCellFactory(cellFoctory);
        TableCart.setItems( cartService.getNftfromCart());
        float tot=0;
        for (int i=0;i<cartService.getPricefromNftCart().size();i++)
        {
            tot=tot+cartService.getPricefromNftCart().get(i).getPrice();
        }
        if(tot!=0)
        {
            totalId.setText(tot+"");
        }
        else
        {
            totalId.setText("0");
        }
        TransactionService transactionService=new TransactionService();
        double balance=0;
        for (int i=0;i<transactionService.getwalletIdClient(1).size();i++)
        {
            balance=balance+transactionService.getwalletIdClient(1).get(i).getBalance();
            if(balance>=tot)
            {
                cartWalletBtn.setDisable(false);
                StripebtnWallet.setDisable(false);
            }
            else
            {
                cartWalletBtn.setDisable(true);
                StripebtnWallet.setDisable(true);
            }

                if(totalId.getText()=="0")
                {
                    cartWalletBtn.setDisable(true);
                    StripebtnWallet.setDisable(true);
                }
        }



    }

    public void search()
    {

        CartService cartService=new CartService();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Nft> filteredData = new FilteredList<>(cartService.getNftfromCart(), b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nft -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();


                if (String.valueOf(nft.getTitle()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Nft> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableCart.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableCart.setItems(sortedData);
    }


    public void PayCartTransaction(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void PayCartStripe(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("PaiementStripe.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }


}
