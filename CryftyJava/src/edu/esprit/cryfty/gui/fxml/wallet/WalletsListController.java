package edu.esprit.cryfty.gui.fxml.wallet;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.gui.Main;
import edu.esprit.cryfty.service.WalletService;
import edu.esprit.cryfty.utils.MailUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.mail.MessagingException;
import javax.management.Notification;

public class WalletsListController {

    @FXML
    private TableView<Wallet> personTable;
    @FXML
    private TableColumn<Wallet, String> firstNameColumn;
    @FXML
    private TableColumn<Wallet, String> lastNameColumn;

    @FXML
    private Label walltLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label balanceLabl;
    @FXML
    private Label mainLabel;
    @FXML
    private Label activeLabel;
    @FXML
    private Label nodeLabel;

    @FXML
    private Button add;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ImageView imageView;

    @FXML
    private Button buttonUpload;

    @FXML
    private Button activationButton;

    @FXML
    private Button miningButton;
    @FXML
    private Button transferButton;


    private Desktop desktop = Desktop.getDesktop();
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WalletsListController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().walletLabelProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().walletAddressProperty());

        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        final FileChooser fileChooser = new FileChooser();
        buttonUpload.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                File binaryFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
                if (binaryFile != null) {
                    List<File> files = Arrays.asList(binaryFile);
                    Wallet selectedItem = personTable.getSelectionModel().getSelectedItem();
                    String url = "http://127.0.0.1:8000/api/wallet/upload-image/"+selectedItem.getId();
                    String charset = "UTF-8";
                    String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
                    String CRLF = "\r\n"; // Line separator required by multipart/form-data.

                    URLConnection connection = null;
                    try {
                        connection = new URL(url).openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                    try (
                            OutputStream output = connection.getOutputStream();
                            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
                    ) {
                        // Send binary file.
                        writer.append("--" + boundary).append(CRLF);
                        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
                        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
                        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                        writer.append(CRLF).flush();
                        Files.copy(binaryFile.toPath(), output);
                        output.flush(); // Important before continuing with writer!
                        writer.append(CRLF).flush(); // indicates end of boundary.

                        // End of multipart/form-data.
                        writer.append("--" + boundary + "--").append(CRLF).flush();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int responseCode = 0;
                    try {
                        responseCode = ((HttpURLConnection) connection).getResponseCode();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(responseCode == 200){

                        if (selectedItem != null) {
                            WalletService walletService = new WalletService();
                            personTable.setItems(walletService.getWallets());
                            String title = "Uploaded ! ";
                            String message = "Your image has been updated";
                            NotificationType notification = NotificationType.SUCCESS;

                            TrayNotification tray = new TrayNotification();
                            tray.setTitle(title);
                            tray.setMessage(message);
                            tray.setNotificationType(notification);
                            tray.showAndWait();
                            }
                    }
                }
            }
        });
    }



    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        WalletService walletService = new WalletService();
        personTable.setItems(walletService.getWallets());

    }

    private void showPersonDetails(Wallet wallet) {
        if (wallet != null) {

            // Fill the labels with info from the person object.
            walltLabel.setText(wallet.getWalletLabel());
            addressLabel.setText(wallet.getWalletAddress());
            balanceLabl.setText(String.valueOf(wallet.getBalance()));
            mainLabel.setText(Boolean.toString(wallet.isIsMain()));
            activeLabel.setText(Boolean.toString(wallet.isIsActive()));
            nodeLabel.setText(wallet.getNode().getNodeLabel() + "(" +wallet.getNode().getCoinCode() +")" );
            if (!wallet.isIsActive()){
                titleLabel.setText("Wallet Details (This wallet is Inactive)");
                activationButton.setVisible(true);
                miningButton.setVisible(false);
                transferButton.setVisible(false);
            }
            else {
                titleLabel.setText("Wallet Details");
                    activationButton.setVisible(false);
                    miningButton.setVisible(true);
                transferButton.setVisible(true);
            }

            String imageSource = "http://127.0.0.1:8000/uploads/walletImages/"+wallet.getWalletImageFileName();

            Image image =new Image(imageSource);
            imageView.setImage(image);

            buttonUpload.setVisible(true);



        } else {
            // Person is null, remove all the text.
            walltLabel.setText("");
            addressLabel.setText("");
            balanceLabl.setText("");
            mainLabel.setText("");
            activeLabel.setText("");
            nodeLabel.setText("");
            buttonUpload.setVisible(false);
            activationButton.setVisible(false);
            miningButton.setVisible(false);
            transferButton.setVisible(false);
        }
    }

    @FXML
    void deleteClient(ActionEvent event){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int id = personTable.getSelectionModel().selectedItemProperty().getValue().getId();
            WalletService walletService = new WalletService();
            walletService.deleteWallet(id);
            personTable.setItems(walletService.getWallets());
            String title = "Wallet Deleted";
            String message = "Your wallet has been deleted";
            NotificationType notification = NotificationType.WARNING;

            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotificationType(notification);
            tray.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Wallet Selected");
            alert.setContentText("Please select a wallet in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    void mineCrypto(ActionEvent event){
        Wallet selectedItem = personTable.getSelectionModel().getSelectedItem();
        WalletService walletService = new WalletService();
        walletService.mineCrypto(selectedItem);
        personTable.setItems(walletService.getWallets());
        String title = "Balance Added";
        String message = "You added crypto to your balance ! Congratulations !";
        NotificationType notification = NotificationType.SUCCESS;
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notification);
        tray.showAndWait();
    }
    @FXML
    void sendVerificationEmail(ActionEvent event) throws MessagingException {
        Wallet selectedItem = personTable.getSelectionModel().getSelectedItem();
        MailUtil.sendMail(String.valueOf(selectedItem.getId()));
        String title = "Email Sent";
        String message = "Activation link sent to your email !";
        NotificationType notification = NotificationType.SUCCESS;

        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notification);
        tray.showAndWait();
    }

    @FXML
    void openCalculator(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/wallet/calculator.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Calculator");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        CalculatorController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    @FXML
    void openTransferDialog(ActionEvent event) throws IOException {
        Wallet selectedPerson = personTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/wallet/TransferDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Calculator");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        TransferDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setWallet(selectedPerson);


        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        personTable.setItems(new WalletService().getWallets());
    }

    @FXML
    private void handleNewPerson() {
        Wallet tempPerson = new Wallet();
        boolean okClicked = this.showPersonAddDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
            showPersonDetails(tempPerson);
            WalletService walletService = new WalletService();
            personTable.setItems(walletService.getWallets());
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Wallet selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = this.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                personTable.setItems(new WalletService().getWallets());
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Wallet Selected");
            alert.setContentText("Please select a wallet in the table.");

            alert.showAndWait();
        }
    }

    public boolean showPersonEditDialog(Wallet wallet) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fxml/wallet/WalletEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Wallet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            WalletEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWallet(wallet);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showPersonAddDialog(Wallet wallet) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fxml/wallet/WalletAddDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Wallet");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            WalletAddDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWallet(wallet);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
