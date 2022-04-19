package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.entity.payment.Transaction;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.service.payment.TransactionService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AffichetransactionController implements Initializable {
    @javafx.fxml.FXML
        private TableColumn<Transaction, String> AmountId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> dateTransactionId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> RefId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> wAddressId;
    @javafx.fxml.FXML
    private TableView<Transaction> TableTransaction;
    @javafx.fxml.FXML
    private TextField searchId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getTransactionClient();
        search();
    }

    public void getTransactionClient()
    {
        TransactionService transactionService=new TransactionService();
        System.out.println(transactionService.getTransactionsByClient(1));
        AmountId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                return new SimpleStringProperty(param.getValue().getCartId().getTotal().toString());
            }
        });
        dateTransactionId.setCellValueFactory(new PropertyValueFactory<>("datetransaction"));
        RefId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wAddressId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                return new SimpleStringProperty(param.getValue().getWallets().getWalletAddress());
            }
        });

        TableTransaction.setItems(transactionService.getTransactionsByClient(1));
    }

    public void search()
    {

        TransactionService transactionService=new TransactionService();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Transaction> filteredData = new FilteredList<>(transactionService.getTransactionsByClient(1), b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transaction -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();


                if (String.valueOf(transaction.getWallets().getWalletAddress()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableTransaction.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableTransaction.setItems(sortedData);
    }
}
