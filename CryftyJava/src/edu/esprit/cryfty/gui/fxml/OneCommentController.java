package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.OneItemController.comment;

public class OneCommentController implements Initializable {
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Pane pnlComment;
    @javafx.fxml.FXML
    public Button btnUpdate;
    @javafx.fxml.FXML
    private Label lblOwner;
    @javafx.fxml.FXML
    private Label lblPostDate;

    private final NftComment thisComment = comment;
    @FXML
    private TextField tfComment;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location+"\n"+resources);
        lblOwner.setText(thisComment.getUser().getUsername()+": ");
        Double ownerX = lblOwner.getLayoutX();
        tfComment.setText(thisComment.getContent());
        tfComment.setLayoutX(ownerX+84);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at'\nHH:mm");
        lblPostDate.setText(thisComment.getPostDate().format(formatter));
    }

    @FXML
    public void updateComment(ActionEvent actionEvent) throws IOException {
        tfComment.setEditable(true);
        tfComment.requestFocus();
    }

    @FXML
    public void deleteComment(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional confirmation = alert.showAndWait();
        if(confirmation.get() == ButtonType.CANCEL){
            System.out.println("Cancel");
        }
        else if(confirmation.get() == ButtonType.OK){
            NftCommentService nftCommentService = new NftCommentService();
            nftCommentService.deleteComment(thisComment);
            pnlComment.getChildren().clear();
        }
    }

        @FXML
        private void onKeyPressed(final KeyEvent event) {
            if(event.getCode().getName().equals("Enter")){
                tfComment.setEditable(false);
                thisComment.setContent(tfComment.getText()+" (edited)");
                NftCommentService nftCommentService = new NftCommentService();
                nftCommentService.updateComment(thisComment);
            }
        }
}
