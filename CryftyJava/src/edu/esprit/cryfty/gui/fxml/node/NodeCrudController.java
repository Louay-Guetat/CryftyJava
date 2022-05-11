package edu.esprit.cryfty.gui.fxml.node;

import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.gui.Main;
import edu.esprit.cryfty.gui.fxml.wallet.CalculatorController;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.service.WalletService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NodeCrudController {

    @FXML
    private TextField nodeLabel;

    @FXML
    private TextField nodeCode;

    @FXML
    private TextField nodeReward;

    @FXML
    private TableView<Node> nodeTableView;

    @FXML
    private TableColumn<Node, String> nodeLabelColumn;

    @FXML
    private TableColumn<Node, String> nodeCodeColumn;

    @FXML
    private TableColumn<Node, Double> nodeRewardColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private Main mainApp;

    private Stage dialogStage;

    public NodeCrudController() {
    }


    @FXML
    private void initialize() {
        nodeLabelColumn.setCellValueFactory(nodeStringCellDataFeatures -> nodeStringCellDataFeatures.getValue().nodeLabelProperty());
        nodeCodeColumn.setCellValueFactory(nodeStringCellDataFeatures -> nodeStringCellDataFeatures.getValue().coinCodeProperty());
        nodeRewardColumn.setCellValueFactory(nodeStringCellDataFeatures -> nodeStringCellDataFeatures.getValue().nodeRewardProperty().asObject());
        nodeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNodeDetails(newValue));
        NodeService nodeService = new NodeService();
        ObservableList<Node> nodes = nodeService.getNodes();
        nodes.forEach(System.out::println);
        nodeTableView.setItems(nodes);

    }

    private void showNodeDetails(Node node) {
        if (node != null) {
            // Fill the labels with info from the person object.
            nodeLabel.setText(node.getNodeLabel());
            nodeCode.setText(node.getCoinCode());
            nodeReward.setText(String.valueOf(node.getNodeReward()));


        } else {
            // Person is null, remove all the text.
            nodeLabel.setText("");
            nodeCode.setText("");
            nodeReward.setText("");
        }
    }


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        NodeService nodeService = new NodeService();
        ObservableList<Node> nodes = nodeService.getNodes();
        nodes.forEach(System.out::println);
        nodeTableView.setItems(nodes);
    }

    @FXML
    void deleteClient(ActionEvent event) {
        int selectedIndex = nodeTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int id = nodeTableView.getSelectionModel().selectedItemProperty().getValue().getId();
            NodeService nodeService = new NodeService();
            nodeService.deleteNode(id);
            nodeTableView.setItems(nodeService.getNodes());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Node Selected");
            alert.setContentText("Please select a Node in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    void updateNode(ActionEvent event) {
        int selectedIndex = nodeTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if(isValid()){
            Node node = nodeTableView.getSelectionModel().getSelectedItem();
            NodeService nodeService = new NodeService();
            node.setNodeLabel(nodeLabel.getText());
            node.setCoinCode(nodeCode.getText());
            node.setNodeReward(Double.parseDouble(nodeReward.getText()));
            nodeService.updateNode(node);
            nodeTableView.setItems(nodeService.getNodes());}
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Node Selected");
            alert.setContentText("Please select a Node in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    void addNode(ActionEvent event) {
        if (isValid()) {
            Node node = new Node();
            NodeService nodeService = new NodeService();
            node.setNodeLabel(nodeLabel.getText());
            node.setCoinCode(nodeCode.getText());
            node.setNodeReward(Double.parseDouble(nodeReward.getText()));
            nodeService.addNod(node);
            nodeTableView.setItems(nodeService.getNodes());
        }
    }

    @FXML
    void openStats(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/node/Stats.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Stats");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        StatsController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }
    private boolean isValid() {
        String errorMessage = "";

        if (nodeLabel.getText() == null || nodeLabel.getText().length() <= 4) {
            errorMessage += "No valid Label!\n";
        }

        if (nodeCode.getText() == null || nodeCode.getText().length() > 5 || nodeCode.getText().length() <= 0) {
            errorMessage += "No valid Code!\n";
        }

        if (nodeReward.getText() == null || nodeReward.getText().length() <= 0) {
            errorMessage += "No valid Reward!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
