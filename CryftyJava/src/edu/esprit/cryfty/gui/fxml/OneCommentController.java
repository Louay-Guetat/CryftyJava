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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.OneItemController.comment;
import static edu.esprit.cryfty.gui.fxml.OneItemController.tfComment;

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
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("OneItem.fxml"));
        Parent root = loader.load();
        OneItemController oneItemController = loader.getController();
        oneItemController.setComment(lblComment.getText());
        oneItemController.btnComment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NftCommentService nftCommentService = new NftCommentService();
                comment.setContent(oneItemController.tfComment.getText());
                nftCommentService.updateComment(comment);
            }
        });

        Stage stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
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
        NftCommentService nftCommentService = new NftCommentService();
        nftCommentService.deleteComment(thisComment);
    }
}
