//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.gui;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private double x;
    private double y;
    public static User currentUser = new Client(1, "Louay.Guetat");
    public static Stage stage;

    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("fxml/Home.fxml"));
        primaryStage.setScene(new Scene(root));
        root.setOnMousePressed((event) -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - this.x);
            primaryStage.setY(event.getScreenY() - this.y);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
