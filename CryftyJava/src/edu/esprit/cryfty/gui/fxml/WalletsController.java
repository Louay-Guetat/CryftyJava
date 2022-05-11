package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Transfer;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.gui.Main;
import edu.esprit.cryfty.gui.fxml.node.NodeCrudController;
import edu.esprit.cryfty.gui.fxml.wallet.WalletsListController;
import edu.esprit.cryfty.service.WalletService;
import edu.esprit.cryfty.service.user.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WalletsController implements Initializable {
    @FXML
    private Pane pnlOverview;
    @FXML
    private Label walletBalance;
    @FXML
    private Label walletNode;
    @FXML
    private Label walletLabel;
    @FXML
    private Label walletAddress;
    @FXML
    private VBox pnItems;
    @FXML
    private TableView<Transfer> tableView;
    @FXML
    private TableColumn<Transfer, String> dateColumn;
    @FXML
    private TableColumn<Transfer, String> senderColumn;
    @FXML
    private TableColumn<Transfer, String> receiverColumn;
    @FXML
    private TableColumn<Transfer, String> amountColumn;
    @FXML
    private Button btnWallets;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.getClientMainWallet(Session.getInstance().getCurrentUser().getId());
        walletBalance.setText(String.valueOf(wallet.getBalance()) + " " +wallet.getNode().getCoinCode());
        walletNode.setText(wallet.getNode().getNodeLabel());
        System.out.println(wallet.getWalletLabel());
        walletLabel.setText(wallet.getWalletLabel());
        walletAddress.setText(wallet.getWalletAddress());
        ArrayList<Transfer> transfers = walletService.getTransfers();
        System.out.println(transfers);
        ObservableList<Transfer> transferObservableList = FXCollections.observableList(transfers);
        tableView.setItems(transferObservableList);
        dateColumn.setCellValueFactory(transferDateCellDataFeatures -> transferDateCellDataFeatures.getValue().dateProperty());
        senderColumn.setCellValueFactory(transferDateCellDataFeatures -> transferDateCellDataFeatures.getValue().senderProperty());
        receiverColumn.setCellValueFactory(transferDateCellDataFeatures -> transferDateCellDataFeatures.getValue().receiverProperty());
        amountColumn.setCellValueFactory(transferDateCellDataFeatures -> transferDateCellDataFeatures.getValue().amountProperty());
    }

    @FXML
    public void refresh(){
        WalletService walletService = new WalletService();
        ArrayList<Transfer> transfers = walletService.getTransfers();
        System.out.println(transfers);
        ObservableList<Transfer> transferObservableList = FXCollections.observableList(transfers);
        tableView.setItems(transferObservableList);
    }

    public void handleClicks(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnWallets) {
            try {
                // Load person overview.
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wallet/WalletsList.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("All Wallets");
                stage.setScene(new Scene(root1));
                stage.setMinHeight(559);
                stage.setMinWidth(944);
                WalletsListController controller = fxmlLoader.getController();
                controller.setMainApp(new Main());
                stage.show();
                // Set person overview into the center of root layout.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
