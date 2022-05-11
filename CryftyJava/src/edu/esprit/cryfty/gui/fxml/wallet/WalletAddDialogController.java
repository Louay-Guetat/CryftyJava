package edu.esprit.cryfty.gui.fxml.wallet;

import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.service.WalletService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WalletAddDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private ChoiceBox<Node> choiceBox;



    private Stage dialogStage;
    private Wallet wallet;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param wallet
     */
    public void setWallet(Wallet wallet) {
        this.wallet = new Wallet();
        firstNameField.setText("");
        NodeService nodeService = new NodeService();
        choiceBox.setItems(nodeService.getNodes());

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            WalletService walletService = new WalletService();
            wallet.setIsMain(false);
            wallet.setIsActive(false);
            wallet.setWalletAddress(walletService.generateAddress());
            Client client = new Client();
            client.setId(1);
            wallet.setClient(client);
            wallet.setWalletImageFileName("default-image.jpg");
            wallet.setBalance(0);
            wallet.setWalletLabel(firstNameField.getText());
            wallet.setNode(choiceBox.getValue());
            walletService.addWallet(wallet);
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() <= 5) {
            errorMessage += "No valid Label!\n";
        }
        if (choiceBox.getSelectionModel().getSelectedItem() == null){
            errorMessage += "No Node Selected \n";
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
