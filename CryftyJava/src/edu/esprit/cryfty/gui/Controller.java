package edu.esprit.cryfty.gui;

import edu.esprit.cryfty.entity.Transfer;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.gui.fxml.node.NodeCrudController;
import edu.esprit.cryfty.gui.fxml.wallet.WalletsListController;
import edu.esprit.cryfty.service.WalletService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Label walletBalance;
    @FXML
    private Label walletNode;
    @FXML
    private Label walletLabel;
    @FXML
    private Label walletAddress;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.getClientMainWallet(2);
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
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            try {
                // Load person overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("fxml/node/NodeCrud.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(personOverview));
                NodeCrudController controller = loader.getController();
                controller.setMainApp(new Main());
                stage.show();
                // Set person overview into the center of root layout.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            try {
                // Load person overview.
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/wallet/WalletsList.fxml"));
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
