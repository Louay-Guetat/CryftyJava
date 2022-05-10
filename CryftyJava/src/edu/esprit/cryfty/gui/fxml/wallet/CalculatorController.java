package edu.esprit.cryfty.gui.fxml.wallet;

import edu.esprit.cryfty.entity.Coin;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.service.NodeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

public class CalculatorController {
    @FXML
    private TextField input;
    @FXML
    private TextField output;
    @FXML
    private ChoiceBox<Coin> choiceBox;



    private Stage dialogStage;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() throws IOException {
        output.setDisable(true);
        NodeService nodeService = new NodeService();
        choiceBox.setItems(nodeService.getCoinsValue());
        System.out.println(nodeService.getCoinsValue().get(0).getClass());
        System.out.println(nodeService.getCoinsValue().get(0).getType());
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isValidInput(input.getText()) && choiceBox.getSelectionModel().getSelectedItem() != null){
        Coin coin = choiceBox.getSelectionModel().getSelectedItem();
        double result = Double.parseDouble(input.getText()) * Double.parseDouble(coin.getValue());
        output.setText(String.valueOf(result));
        }
    }

    private boolean isValidInput(String text) {
        if (text == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Not A Number");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
