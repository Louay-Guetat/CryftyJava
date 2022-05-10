package edu.esprit.cryfty.gui.fxml.wallet;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.service.WalletService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class TransferDialogController {
    private Stage dialogStage;
    private Wallet wallet;
    private boolean okClicked = false;

    @FXML
    private TextField sentTextField;

    @FXML
    private TextField amountTextField;


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
        this.wallet = wallet;
    }

    @FXML
    public void transfer(){
        if(isValidInput(amountTextField.getText())){
        String sent = "\""+sentTextField.getText()+"\"";
        double amount = Double.parseDouble(amountTextField.getText());
        WalletService walletService = new WalletService();
        String title = "ERROR ! ";
        String message = "Transfer Failed";
        NotificationType notification = NotificationType.ERROR;
        if (walletService.transferCrypto(wallet,sent,amount)){
             title = "Sent ! ";
             message = "Transfer successful";
             notification = NotificationType.SUCCESS;
        }
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notification);
        tray.showAndWait();}
    }
    private boolean isValidInput(String text) {
        if (wallet.getBalance() < Double.parseDouble(amountTextField.getText())){
            return false;
        }
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
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
