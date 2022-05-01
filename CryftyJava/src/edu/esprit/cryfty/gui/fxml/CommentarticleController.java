package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogComment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import static edu.esprit.cryfty.gui.fxml.PostdetailsController.comment;

public class CommentarticleController implements Initializable {
    @javafx.fxml.FXML
    private Label lblComment;
    @javafx.fxml.FXML
    private Label lblOwner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BlogComment thisComment = comment;
        lblComment.setText(thisComment.getComment());
        lblOwner.setText(comment.getUser().getUsername() +"                                     "+ comment.getPostDate());
    }
}
