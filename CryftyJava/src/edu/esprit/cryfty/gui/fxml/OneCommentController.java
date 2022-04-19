package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.NftComment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.OneItemController.comment;

public class OneCommentController implements Initializable {
    @javafx.fxml.FXML
    private Label lblComment;
    @javafx.fxml.FXML
    private Label lblOwner;
    @javafx.fxml.FXML
    private Label lblReactions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NftComment thisComment = comment;
        lblComment.setText(thisComment.getContent());
        lblOwner.setText(comment.getUser().getUsername() +" commented on "+ comment.getPostDate());
    }
}
