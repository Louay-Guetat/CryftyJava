package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.OneItemController.comment;

public class OneCommentController implements Initializable {
    @javafx.fxml.FXML
    public Label lblComment;
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


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location+"\n"+resources);
        lblOwner.setText(thisComment.getUser().getUsername()+": ");
        Double ownerX = lblOwner.getLayoutX();
        lblComment.setText(thisComment.getContent());
        lblComment.setLayoutX(ownerX+95);
        lblPostDate.setText(thisComment.getPostDate()+"");
    }

    @FXML
    public void updateComment(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog(thisComment.getContent());
        dialog.setTitle("Updating Comment");
        dialog.setHeaderText("Change your comment:");
        dialog.setContentText("Comment:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(comment -> {
            thisComment.setContent(comment);
            NftCommentService nftCommentService = new NftCommentService();
            nftCommentService.updateComment(thisComment);
            lblComment.setText(comment);
        });
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
        }
    }
}
