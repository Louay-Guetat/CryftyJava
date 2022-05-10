package edu.esprit.cryfty.gui;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.gui.fxml.wallet.WalletEditDialogController;
import edu.esprit.cryfty.service.NodeService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private ObservableList<Wallet> wallets = FXCollections.observableArrayList();
    private double x, y;


    public Main() {
        wallets.add(new Wallet("Label1","5161321323552"));
        wallets.add(new Wallet("Label2","5161321321352"));
        wallets.add(new Wallet("Label3","5161321321345"));
        wallets.add(new Wallet("Label4","51613213223432"));
        wallets.add(new Wallet("Label5","5161321312132"));


    }
    public ObservableList<Wallet> getPersonData() {
        return wallets;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        primaryStage.setScene(new Scene(root));
        //set stage borderless
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        NodeService nodeService = new NodeService();
        try {
            nodeService.getCoinsValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
