package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.entity.payment.Transaction;
import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.service.payment.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TransactionController  implements Initializable {
    @javafx.fxml.FXML
    private Button Paiementbtn;
    @javafx.fxml.FXML
    private ComboBox addressWalletId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TransactionService transactionService=new TransactionService();
        ArrayList<Wallet> transactionWallet=transactionService.getwalletIdClient(1);
        String [] walletAdress=new String[transactionWallet.size()];
        for (int i=0;i<transactionWallet.size();i++)
        {
            walletAdress[i]=transactionWallet.get(i).getWalletAddress();
        }
        ObservableList<String> listAddressWallet= FXCollections.observableArrayList(walletAdress);
        addressWalletId.setItems(listAddressWallet);

    }


    public void addPaiement(ActionEvent actionEvent) throws IOException {
            CartService cartService=new CartService();
            TransactionService transactionService=new TransactionService();
            if(addressWalletId.getSelectionModel().isEmpty()==true)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Choose a wallet please.");
                alert.show();
            }
            else {
                Date d = new Date();
                Cart cartAjout = cartService.getCartfromNft().get(0);
                String a = addressWalletId.getSelectionModel().getSelectedItem().toString();
                Wallet adw = transactionService.getwalletsAddress(a);
                Transaction transaction = new Transaction(cartAjout, adw, d);
                transactionService.addTransaction(transaction);

                float tot = 0;
                if (cartService.getNftfromCart().size() > 0) {
                    for (int i = 0; i < cartService.getPricefromNftCart().size(); i++) {
                        tot = tot + cartService.getPricefromNftCart().get(i).getPrice();
                    }
                    cartAjout.setTotal(tot);
                    cartService.modifierCartTotal(tot, cartAjout.getId());
                } else {
                    cartAjout.setTotal(0);
                }
                cartService.supprimerCartfromNftCart(cartAjout.getId());
                Pay(actionEvent);
            }
    }

    public void Pay(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
